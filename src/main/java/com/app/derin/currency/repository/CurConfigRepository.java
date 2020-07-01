package com.app.derin.currency.repository;

import com.app.derin.currency.domain.CurConfig;

import com.app.derin.currency.domain.CurCurrencyMatrix;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CurConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurConfigRepository extends JpaRepository<CurConfig, Long> {
    @Query(value = "select distinct cu.currency_import_time from cur_config cu where cu.id = :id", nativeQuery = true)
    String findImportTimeById(@Param("id") Long id);
}
