package com.desklamp.gateway.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户调用服务统计
 * @author by Joney on 2019/4/9 14:18
 */
@Data
@Entity
@Table(name = "client_svc_invoke")
public class ClientSvcInvoke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "serviceName")
    private String serviceName;
    @Column(name = "requestUri")
    private String requestUri;
    @Column(name = "clientUser")
    private String clientUser;
    @Column(name = "invokeCount")
    private Integer invokeCount;
    @Column(name = "invokeTime")
    private Date invokeTime;

    public ClientSvcInvoke() {

    }

    public ClientSvcInvoke(String serviceName, String requestUri, String clientUser) {
        this.serviceName = serviceName;
        this.requestUri = requestUri;
        this.clientUser = clientUser;
    }
}
