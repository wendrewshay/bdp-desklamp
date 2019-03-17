package com.desklamp.enterprise.data.controller;

import com.desklamp.enterprise.data.domain.CompanyInfo;
import com.desklamp.enterprise.data.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/{id}")
    public CompanyInfo findById(@PathVariable Integer id) {
        CompanyInfo companyInfo = this.companyRepository.findById(id).get();
        return companyInfo;
    }
}
