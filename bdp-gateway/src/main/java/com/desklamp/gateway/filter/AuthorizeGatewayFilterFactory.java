package com.desklamp.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 网关token认证过滤器
 * @author by Joney on 2019/3/22 15:13
 */
@Slf4j
@Component
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {

    private static final String AUTHORIZE_TOKEN = "token";
    private static final String AUTHORIZE_UID = "uid";

    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
        log.info(">>> Loaded GatewayFilterFactory [Authorize]");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if (!config.isEnabled()) {
                    return chain.filter(exchange);
                }
                // 从headers中获取uid和token
                ServerHttpRequest request = exchange.getRequest();
                HttpHeaders headers = request.getHeaders();
                String token = headers.getFirst(AUTHORIZE_TOKEN);
                String uid = headers.getFirst(AUTHORIZE_UID);
                log.info(">>> From request headers: uid = {}, token= {}", uid, token);

                // 若headers中无uid和token，返回禁止访问状态
                ServerHttpResponse response = exchange.getResponse();
                if (StringUtils.isEmpty(token) || StringUtils.isEmpty(uid)) {
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    return response.setComplete();
                }

                // 对uid和token进行校验，若校验失败则返回授权失败状态
                String authToken = redisTemplate.opsForValue().get("uid").block();
                log.info(">>> From redis: uid = {}, authToken = {}", uid, authToken);
                if (StringUtils.isEmpty(authToken) || !authToken.equals(token)) {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
                return chain.filter(exchange);
            }
        };
    }

    /**
     * Returns hints about the number of args and the order for shortcut parsing.
     *
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    public static class Config {
        /**
         * 控制是否开启认证
         */
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
