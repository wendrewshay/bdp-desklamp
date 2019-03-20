package com.desklamp.enterprise.data.service;

import com.desklamp.enterprise.data.mapper.company.CompanyMapper;
import com.desklamp.enterprise.data.domain.CompanyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 示例
 */
@Service
public class CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    public CompanyInfo queryById(int id) {
        return companyMapper.findById(id);
    }
}
