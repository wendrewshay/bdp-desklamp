package com.desklamp.gateway.security.jwt;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义JWT-token认证过滤器
 * @author by Joney on 2019/3/26 17:53
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_NAME = "Authorization";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        String token = request.getHeader(HEADER_NAME);

        if (!StringUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            TokenAuthenticationHandler tokenAuthenticationHandler = new TokenAuthenticationHandler();
            String subjectStr = tokenAuthenticationHandler.getSubjectFromToken(token.split(" ")[1]);

            // 判断从token中取出来的subject是否为空
            if (!StringUtils.isEmpty(subjectStr)) {
                JSONObject subject = JSONObject.parseObject(subjectStr);
                JSONArray authorities = subject.getJSONArray("authorities");
                String username = subject.getString("username");
                // 判断是否包含权限
                boolean hasPermission = false;
                String serviceName = null;
                if (!CollectionUtils.isEmpty(authorities)) {
                    for (Object authority : authorities) {
                        String authorityName = ((JSONObject) authority).getString("authority");
                        if (requestURI.contains(authorityName)) {
                            hasPermission = true;
                            serviceName = authorityName;
                            break;
                        }
                    }
                }
                // 若包含权限就将认证token放入安全上下文中
                if (hasPermission) {
                    request.setAttribute("currentUser", username);
                    request.setAttribute("serviceName", serviceName);
                    SecurityContextHolder.getContext().setAuthentication(new JWTAuthenticationToken(subjectStr));
                }
            }
        }

        filterChain.doFilter(request, servletResponse);
    }
}
