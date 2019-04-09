package com.desklamp.gateway.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 客户账户表
 * @author by Joney on 2019/4/9 11:25
 */
@Data
@Entity
@Table(name = "client_account")
public class ClientAccount implements Serializable {

    @Id
    @GeneratedValue(generator = "uuidGenerator")
    @GenericGenerator(name="uuidGenerator", strategy = "uuid")
    private String userid;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private boolean enabled;
    @Column
    private boolean accountNonExpired;
    @Column
    private boolean credentialsNonExpired;
    @Column
    private boolean accountNonLocked;
    @Column
    private Date createTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_relations", joinColumns = {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "authorityName")})
    private List<ClientAuthority> authorities;
}
