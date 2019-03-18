package com.desklamp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BdpGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdpGatewayApplication.class, args);
    }

}
