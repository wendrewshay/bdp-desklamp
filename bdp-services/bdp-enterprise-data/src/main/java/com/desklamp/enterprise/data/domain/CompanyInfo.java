package com.desklamp.enterprise.data.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "company_info")
public class CompanyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="creditCode")
    private String creditCode;

    @Column(name="comName")
    private String comName;
}
