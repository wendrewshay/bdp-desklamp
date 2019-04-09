package com.desklamp.gateway.config;

import com.desklamp.gateway.contants.CacheConstants;
import com.desklamp.gateway.domain.ClientAuthority;
import com.desklamp.gateway.filter.PostSvcResponseFilter;
import com.desklamp.gateway.filter.PreRequestLogFilter;
import com.desklamp.gateway.repository.ClientAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 网关配置
 * @author by Joney on 2019/3/25 14:18
 */
@Configuration
public class GatewayConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private RedisTemplate redisTemplate;

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
     * 注册服务调用统计过滤器
     * @return PostSvcResponseFilter
     * @author by Joney on 2019/4/9 14:50
     */
    @Bean
    public PostSvcResponseFilter postSvcResponseFilter() {
        return new PostSvcResponseFilter();
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

    /**
     * 上下文准备好后触发事件：取出所有权限放入缓存
     * @param applicationReadyEvent 事件参数
     * @author by Joney on 2019/4/9 10:59
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        ConfigurableApplicationContext context = applicationReadyEvent.getApplicationContext();
        ClientAuthorityRepository authorityRepository = context.getBean(ClientAuthorityRepository.class);

        List<ClientAuthority> authorities = authorityRepository.findAll();
        redisTemplate.delete(CacheConstants.AUTHORITY_CACHE_KEY);
        redisTemplate.opsForList().rightPushAll(CacheConstants.AUTHORITY_CACHE_KEY, authorities);
    }
}
