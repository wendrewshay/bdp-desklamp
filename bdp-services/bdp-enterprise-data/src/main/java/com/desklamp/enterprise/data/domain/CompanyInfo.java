package com.desklamp.enterprise.data.domain;

import lombok.Data;

import javax.persistence.*;

@Data
public class CompanyInfo {

    private Integer id;
    private String creditCode;
    private String comName;
}
