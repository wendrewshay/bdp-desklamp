package com.desklamp.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.desklamp.gateway.contants.CacheConstants;
import com.desklamp.gateway.domain.ClientAuthority;
import com.desklamp.gateway.domain.ClientSvcInvoke;
import com.desklamp.gateway.repository.ClientAuthorityRepository;
import com.desklamp.gateway.service.ClientSvcInvokeService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/**
 * 对调用服务的响应进行处理
 * @author by Joney on 2019/4/9 10:46
 */
@Slf4j
public class PostSvcResponseFilter extends ZuulFilter {

    @Autowired
    private ClientAuthorityRepository authorityRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ClientSvcInvokeService clientSvcInvokeService;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        // 查询缓存中的权限，没有查到则从数据库中查询然后放入缓存
        List<ClientAuthority> authorities = redisTemplate.opsForList().range(CacheConstants.AUTHORITY_CACHE_KEY, 0, -1);
        if (CollectionUtils.isEmpty(authorities)) {
            authorities = authorityRepository.findAll();
        }
        if (!CollectionUtils.isEmpty(authorities)) {
            redisTemplate.delete(CacheConstants.AUTHORITY_CACHE_KEY);
            redisTemplate.opsForList().rightPushAll(CacheConstants.AUTHORITY_CACHE_KEY, authorities);
        } else {
            return false;
        }
        // 权限匹配，匹配到则返回真
        String requestURI = request.getRequestURI();
        Iterator<ClientAuthority> iterator = authorities.iterator();
        while(iterator.hasNext()) {
            ClientAuthority authority = iterator.next();
            if (requestURI.contains(authority.getAuthorityName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        Object currentUser = request.getAttribute("currentUser");
        Object serviceName = request.getAttribute("serviceName");
        String requestURI = request.getRequestURI();

        InputStream inputStream = context.getResponseDataStream();
        String body = null;
        try {
            body = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error(">>> 异常：{}", e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("流关闭异常 >>> {}", e.getMessage(), e);
                }
            }
        }
        if (body != null) {
            JSONObject responseObject = JSONObject.parseObject(body);
            String code = responseObject.getString("code");
            if (code.equals("200")) {
                clientSvcInvokeService.save(new ClientSvcInvoke(serviceName.toString(), requestURI, currentUser.toString()));
            }
        }
        context.setResponseBody(body);
        return null;
    }
}
