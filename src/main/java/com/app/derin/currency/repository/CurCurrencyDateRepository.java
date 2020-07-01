package com.app.derin.currency.repository;

import com.app.derin.currency.domain.CurCurrencyDate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CurCurrencyDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurCurrencyDateRepository extends JpaRepository<CurCurrencyDate, Long>, JpaSpecificationExecutor<CurCurrencyDate> {


}
