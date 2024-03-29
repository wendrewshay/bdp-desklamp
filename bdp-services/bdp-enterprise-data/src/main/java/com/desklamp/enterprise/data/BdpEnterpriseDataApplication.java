package com.desklamp.enterprise.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(value = "com.desklamp.enterprise.data.mapper")
public class BdpEnterpriseDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdpEnterpriseDataApplication.class, args);
    }

}
