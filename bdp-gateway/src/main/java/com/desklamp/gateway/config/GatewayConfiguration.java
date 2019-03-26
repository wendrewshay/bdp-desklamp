package com.desklamp.gateway.config;

import com.desklamp.gateway.filter.PreRequestLogFilter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 网关配置
 * @author by Joney on 2019/3/25 14:18
 */
@Configuration
public class GatewayConfiguration {

    /**
     * 注册请求日志过滤器
     * @return PreRequestLogFilter
     * @author by Joney on 2019/3/25 14:18
     */
    @Bean
    public PreRequestLogFilter preRequestLogFilter() {
        return new PreRequestLogFilter();
    }

    /**
     * 注册支持负载均衡的REST请求模板
     * @return RestTemplate
     * @author by Joney on 2019/3/25 14:36
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
