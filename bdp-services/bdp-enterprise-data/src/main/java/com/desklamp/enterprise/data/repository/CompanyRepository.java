package com.desklamp.enterprise.data.repository;

import com.desklamp.enterprise.data.domain.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyInfo, Integer> {
}
