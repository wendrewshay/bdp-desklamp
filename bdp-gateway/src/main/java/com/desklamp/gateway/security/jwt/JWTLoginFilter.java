package com.desklamp.gateway.security.jwt;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义JWT无状态登录过滤器
 * @author by Joney on 2019/3/26 17:52
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_NAME = "Authorization";

    private AuthenticationSuccessHandler successHandler;

    public JWTLoginFilter() {
    }

    public JWTLoginFilter(AuthenticationManager authManager) {
        setAuthenticationManager(authManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        TokenAuthenticationHandler authenticationHandler = new TokenAuthenticationHandler();
        Object principal = authResult.getPrincipal();
        if (principal != null) {
            UserDetails userDetails = (UserDetails) principal;
            String token = authenticationHandler.generateToken(JSONObject.toJSONString(userDetails));
            response.addHeader(HEADER_NAME, TOKEN_PREFIX + " " + token);
        }
        if (successHandler != null) {
            successHandler.onAuthenticationSuccess(request, response, authResult);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }



    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }
}
