package com.desklamp.enterprise.portrait.config;

import feign.Contract;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign的配置
 */
@Configuration
public class FeignConfiguration {

    /**
     * 将契约配置为Feign原生的默认契约，这样便可以使用feign自带的注解
     * @return
     */
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }

    /**
     * 定义日志级别为FULL
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
