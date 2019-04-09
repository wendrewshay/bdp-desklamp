package com.desklamp.gateway.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户权限
 * @author by Joney on 2019/4/9 11:28
 */
@Data
@Entity
@Table(name = "client_authority")
public class ClientAuthority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorityid;
    @Column
    private String authorityName;
}
