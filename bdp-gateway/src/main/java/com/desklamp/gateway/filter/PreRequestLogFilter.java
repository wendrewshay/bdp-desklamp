package com.desklamp.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 打印请求日志
 * @author by Joney on 2019/3/25 14:10
 */
@Slf4j
public class PreRequestLogFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(">>> REQUEST METHOD: {},  URL：{}", request.getMethod(), request.getRequestURL().toString());
        return null;
    }
}
