package com.desklamp.enterprise.portrait.clients;

import com.desklamp.enterprise.portrait.config.FeignConfiguration;
import com.desklamp.enterprise.portrait.domain.CompanyInfo;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 用feign实现的声明式RESTful API调用
 */
@FeignClient(name="enterprise-data", configuration = FeignConfiguration.class)
public interface CompanyClient {

    /**
     * 根据id查询企业信息
     * @param id
     * @return
     */
    @RequestLine("GET /enterprise-data/company/{id}")
    CompanyInfo findById(@Param("id") Integer id);
}
