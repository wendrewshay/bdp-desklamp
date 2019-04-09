package com.desklamp.gateway.repository;

import com.desklamp.gateway.domain.ClientAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 权限持久操作接口
 * @author by Joney on 2019/4/9 11:54
 */
@Repository
public interface ClientAuthorityRepository extends JpaRepository<ClientAuthority, Integer> {

}
