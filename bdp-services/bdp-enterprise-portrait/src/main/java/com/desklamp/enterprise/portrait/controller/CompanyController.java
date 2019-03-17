package com.desklamp.enterprise.portrait.controller;

import com.desklamp.enterprise.portrait.clients.CompanyClient;
import com.desklamp.enterprise.portrait.domain.CompanyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private CompanyClient companyClient;

    @GetMapping("/{id}")
    public CompanyInfo findById(@PathVariable Integer id) {
        return this.restTemplate.getForObject("http://enterprise-data/enterprise-data/company/" + id, CompanyInfo.class);
    }

    @GetMapping("/log-instance")
    public void logCompanyInstance() {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("enterprise-data");
        log.info(">>> {}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
    }

    @GetMapping("/v2/{id}")
    public CompanyInfo _findById(@PathVariable Integer id) {
        return this.companyClient.findById(id);
    }
}
