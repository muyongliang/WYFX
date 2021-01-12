package com.wyfx.sf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Author: liuxingyu
 * DATE: 2017-06-30.10:50
 * description:
 * version:
 */
@NoRepositoryBean
public interface BaseRepo<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
