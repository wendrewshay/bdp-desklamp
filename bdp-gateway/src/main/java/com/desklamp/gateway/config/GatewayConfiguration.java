package com.desklamp.gateway.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关配置
 */
@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        // 限流配置：令牌桶容量200，请求填充速率为每秒100
//        RequestRateLimiterGatewayFilterFactory.Config rateLimiterConfig = new RequestRateLimiterGatewayFilterFactory.Config();
//        rateLimiterConfig.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
//        rateLimiterConfig.setRateLimiter(new RedisRateLimiter(2, 5));
//        rateLimiterConfig.setKeyResolver(exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()));

        // 路径移除配置
        StripPrefixGatewayFilterFactory.Config stripPrefixConfig = new StripPrefixGatewayFilterFactory.Config();
        stripPrefixConfig.setParts(1);

        // 路由配置
        RouteLocator routeLocator = builder.routes()
                .route("enterprise-data", r -> r.path("/enterprise-data/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://enterprise-data"))
                .route("enterprise-portrait", r -> r.path("/enterprise-portrait/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://enterprise-portrait"))
                .build();
        return routeLocator;
    }

//    @Bean
//    public RequestRateLimiterGatewayFilterFactory rateLimiterGatewayFilterFactory(RateLimiter rateLimiter, @Qualifier("hostAddressKeyResolver") KeyResolver keyResolver) {
//        return new RequestRateLimiterGatewayFilterFactory(rateLimiter, keyResolver);
//    }
//
//    @Bean
//    public RateLimiter rateLimiter() {
//        return new RedisRateLimiter(2, 5);
//    }
//
//    @Bean("hostAddressKeyResolver")
//    public KeyResolver hostAddressKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
//    }
}
