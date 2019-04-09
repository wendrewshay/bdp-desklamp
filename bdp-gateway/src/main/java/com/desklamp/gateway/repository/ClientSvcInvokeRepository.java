package com.desklamp.gateway.repository;

import com.desklamp.gateway.domain.ClientSvcInvoke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 客户调用服务统计持久层操作接口
 * @author by Joney on 2019/4/9 14:24
 */
@Repository
public interface ClientSvcInvokeRepository extends JpaRepository<ClientSvcInvoke, Integer> {

    /**
     * 查询统计记录
     * @param requestUri 请求URI
     * @param clientUser 客户用户名
     * @param startTime  起始时间
     * @param endTime    结束时间
     * @return
     * @author by Joney on 2019/4/9 18:49
     */
    @Query("select c from ClientSvcInvoke c where c.serviceName = ?1 and c.requestUri = ?2 and c.clientUser = ?3 and c.invokeTime between ?4 and ?5")
    ClientSvcInvoke find(String serviceName, String requestUri, String clientUser, Date startTime, Date endTime);
}
