package com.app.derin.currency.service.impl;

import com.app.derin.currency.service.CurCurrenciesService;
import com.app.derin.currency.domain.CurCurrencies;
import com.app.derin.currency.repository.CurCurrenciesRepository;
import com.app.derin.currency.service.dto.CurCurrenciesDTO;
import com.app.derin.currency.service.mapper.CurCurrenciesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CurCurrencies}.
 */
@Service
@Transactional
public class CurCurrenciesServiceImpl implements CurCurrenciesService {

    private final Logger log = LoggerFactory.getLogger(CurCurrenciesServiceImpl.class);

    private final CurCurrenciesRepository curCurrenciesRepository;

    private final CurCurrenciesMapper curCurrenciesMapper;

    public CurCurrenciesServiceImpl(CurCurrenciesRepository curCurrenciesRepository, CurCurrenciesMapper curCurrenciesMapper) {
        this.curCurrenciesRepository = curCurrenciesRepository;
        this.curCurrenciesMapper = curCurrenciesMapper;
    }

    /**
     * Save a curCurrencies.
     *
     * @param curCurrenciesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CurCurrenciesDTO save(CurCurrenciesDTO curCurrenciesDTO) {
        log.debug("Request to save CurCurrencies : {}", curCurrenciesDTO);
        CurCurrencies curCurrencies = curCurrenciesMapper.toEntity(curCurrenciesDTO);
        curCurrencies = curCurrenciesRepository.save(curCurrencies);
        return curCurrenciesMapper.toDto(curCurrencies);
    }

    /**
     * Get all the curCurrencies.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CurCurrenciesDTO> findAll() {
        log.debug("Request to get all CurCurrencies");
        return curCurrenciesRepository.findAll().stream()
            .map(curCurrenciesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one curCurrencies by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurCurrenciesDTO> findOne(Long id) {
        log.debug("Request to get CurCurrencies : {}", id);
        return curCurrenciesRepository.findById(id)
            .map(curCurrenciesMapper::toDto);
    }

    /**
     * Delete the curCurrencies by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurCurrencies : {}", id);
        curCurrenciesRepository.deleteById(id);
    }


}
