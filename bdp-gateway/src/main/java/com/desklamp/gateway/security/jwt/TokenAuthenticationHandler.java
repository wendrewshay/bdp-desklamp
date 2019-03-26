package com.desklamp.gateway.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义token认证处理器
 * @author by Joney on 2019/3/26 17:56
 */
public class TokenAuthenticationHandler implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_SUBJECT = "subject";

    /**
     * 加密密钥
     */
    private static final String DEFAULT_SECRET = "4Zbxk$qz9zmbqNLiYAiRSDu3ocP@1mzM";
    private static final Long DEFAULT_EXPIRATION = 120L;

    private String secret = DEFAULT_SECRET;

    private Long EXPIRATION = DEFAULT_EXPIRATION;

    public TokenAuthenticationHandler() {

    }

    public String getSubjectFromToken(String token) {
        String subject;
        try {
            final Claims claims = getClaimsFromToken(token);
            subject = claims.get(CLAIM_KEY_SUBJECT).toString();
        } catch (Exception e) {
            subject = null;
        }
        return subject;
    }


    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION * 1000);
    }

    public String generateToken(String subject) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_SUBJECT, subject);
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
