package com.app.derin.currency.repository;

import com.app.derin.currency.domain.CurCurrencyMatrix;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CurCurrencyMatrix entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurCurrencyMatrixRepository extends JpaRepository<CurCurrencyMatrix, Long> {
    @Query(value = "select distinct * from cur_currency_matrix cm where cm.source_currency_id = :sourceId and cm.result_currency_id = :resultId", nativeQuery = true)
    CurCurrencyMatrix findBySourceCurrencyAndResultCurrency(@Param("sourceId") Long sourceId, @Param("resultId") Long resultId);


}
