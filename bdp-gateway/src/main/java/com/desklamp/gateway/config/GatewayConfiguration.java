package com.desklamp.gateway.config;

import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 网关配置
 */
@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        // 路由配置
        RouteLocator routeLocator = builder.routes()
                .route("enterprise-data", r -> r.path("/enterprise-data/**")
                        .filters(filterFunc())
                        .uri("lb://enterprise-data"))
                .route("enterprise-portrait", r -> r.path("/enterprise-portrait/**")
                        .filters(filterFunc())
                        .uri("lb://enterprise-portrait"))
                .build();
        return routeLocator;
    }

    public Function<GatewayFilterSpec, UriSpec> filterFunc() {
        return f -> f.stripPrefix(1).requestRateLimiter(config -> {
            config.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            config.setRateLimiter(redisRateLimiter());
            config.setKeyResolver(remoteAddrKeyResolver());
        });
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(5, 20);
    }

    @Bean(name=RemoteAddrKeyResolver.BEAN_NAME)
    public RemoteAddrKeyResolver remoteAddrKeyResolver() {
        return new RemoteAddrKeyResolver();
    }
}
