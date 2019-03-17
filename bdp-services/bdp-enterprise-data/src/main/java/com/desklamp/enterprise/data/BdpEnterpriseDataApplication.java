package com.desklamp.enterprise.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BdpEnterpriseDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdpEnterpriseDataApplication.class, args);
    }

}
