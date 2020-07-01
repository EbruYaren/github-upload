package com.app.derin.currency.service;

import com.app.derin.currency.service.dto.CurConfigDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.app.derin.currency.domain.CurConfig}.
 */
public interface CurConfigService {

    String getCron();

    /**
     * Save a curConfig.
     *
     * @param curConfigDTO the entity to save.
     * @return the persisted entity.
     */
    CurConfigDTO save(CurConfigDTO curConfigDTO);

    /**
     * Get all the curConfigs.
     *
     * @return the list of entities.
     */
    List<CurConfigDTO> findAll();

    /**
     * Get the "id" curConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurConfigDTO> findOne(Long id);

    void setMcfClient();

    /**
     * Delete the "id" curConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
