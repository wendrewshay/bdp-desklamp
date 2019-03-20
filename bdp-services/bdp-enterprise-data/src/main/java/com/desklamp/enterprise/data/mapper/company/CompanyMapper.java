package com.desklamp.enterprise.data.mapper.company;

import com.desklamp.enterprise.data.common.MyMapper;
import com.desklamp.enterprise.data.domain.CompanyInfo;

/**
 * 示例
 */
public interface CompanyMapper extends MyMapper<CompanyInfo> {

    CompanyInfo findById(int id);
}
