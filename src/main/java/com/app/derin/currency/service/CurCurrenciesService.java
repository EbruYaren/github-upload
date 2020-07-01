package com.app.derin.currency.service;

import com.app.derin.currency.service.dto.CurCurrenciesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.app.derin.currency.domain.CurCurrencies}.
 */
public interface CurCurrenciesService {

    /**
     * Save a curCurrencies.
     *
     * @param curCurrenciesDTO the entity to save.
     * @return the persisted entity.
     */
    CurCurrenciesDTO save(CurCurrenciesDTO curCurrenciesDTO);

    /**
     * Get all the curCurrencies.
     *
     * @return the list of entities.
     */
    List<CurCurrenciesDTO> findAll();

    /**
     * Get the "id" curCurrencies.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurCurrenciesDTO> findOne(Long id);

    /**
     * Delete the "id" curCurrencies.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
