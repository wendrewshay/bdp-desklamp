package com.desklamp.gateway.repository;

import com.desklamp.gateway.domain.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 客户持久操作接口
 * @author by Joney on 2019/4/9 11:36
 */
@Repository
public interface ClientAccountRepository extends JpaRepository<ClientAccount, String> {

    /**
     * 根据用户名查询账户
     * @param username 用户名
     * @return ClientAccount
     * @author by Joney on 2019/4/9 11:52
     */
    ClientAccount findByUsername(String username);

}
