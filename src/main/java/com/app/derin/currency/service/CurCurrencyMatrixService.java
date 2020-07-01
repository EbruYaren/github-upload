package com.app.derin.currency.service;

import com.app.derin.currency.domain.CurCurrencyDate;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.dto.CurCurrencyMatrixDTO;
import org.json.JSONArray;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.app.derin.currency.domain.CurCurrencyMatrix}.
 */
public interface CurCurrencyMatrixService {

    /**
     * Update curCurrencyMatrix.
     *
     * @param curCurrencyDateDTO the entity to update.
     */
    void updateTable(CurCurrencyDateDTO curCurrencyDateDTO);

    /**
     * Save a curCurrencyMatrix.
     *
     * @param curCurrencyMatrixDTO the entity to save.
     * @return the persisted entity.
     */
    CurCurrencyMatrixDTO save(CurCurrencyMatrixDTO curCurrencyMatrixDTO);

    /**
     * Get all the curCurrencyMatrices.
     *
     * @return the list of entities.
     */
    List<CurCurrencyMatrixDTO> findAll();

    /**
     * Get the "id" curCurrencyMatrix.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurCurrencyMatrixDTO> findOne(Long id);

    /**
     * Delete the "id" curCurrencyMatrix.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
