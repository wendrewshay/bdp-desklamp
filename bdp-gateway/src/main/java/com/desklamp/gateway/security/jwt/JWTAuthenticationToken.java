package com.desklamp.gateway.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

/**
 * 自定义JWT认证的token
 * @author by Joney on 2019/3/26 17:55
 */
public class JWTAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public JWTAuthenticationToken(Object principal) {
        super(principal, null, Collections.emptyList());
    }

    @Override
    public Object getPrincipal() {
        return super.getPrincipal();
    }

    @Override
    public Object getCredentials() {
        return super.getCredentials();
    }
}
