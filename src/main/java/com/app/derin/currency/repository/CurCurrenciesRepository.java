package com.app.derin.currency.repository;

import com.app.derin.currency.domain.CurCurrencies;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data  repository for the CurCurrencies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurCurrenciesRepository extends JpaRepository<CurCurrencies, Long> {
    @Query(value = "select distinct(id) from cur_currencies where currency_code = :code", nativeQuery = true)
    Long findByCurrencyCode(@Param("code") String code);
}
