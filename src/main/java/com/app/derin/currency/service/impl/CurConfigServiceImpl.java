package com.app.derin.currency.service.impl;

import com.app.derin.currency.config.SchedulingConfiguration;
import com.app.derin.currency.ext.derinfw.DerinfwBusService;
import com.app.derin.currency.service.CurConfigService;
import com.app.derin.currency.domain.CurConfig;
import com.app.derin.currency.repository.CurConfigRepository;
import com.app.derin.currency.service.dto.CurConfigDTO;
import com.app.derin.currency.service.mapper.CurConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CurConfig}.
 */
@Service
@Transactional
public class CurConfigServiceImpl implements CurConfigService {

    private final DerinfwBusService derinfwBusService;

    private final Logger log = LoggerFactory.getLogger(CurConfigServiceImpl.class);

    private final CurConfigRepository curConfigRepository;

    private final CurConfigMapper curConfigMapper;

    private SchedulingConfiguration schedulingConfiguration;


    public CurConfigServiceImpl(DerinfwBusService derinfwBusService, CurConfigRepository curConfigRepository, CurConfigMapper curConfigMapper) {
        this.derinfwBusService = derinfwBusService;
        this.curConfigRepository = curConfigRepository;
        this.curConfigMapper = curConfigMapper;
    }

    public String getCron() {
        return curConfigRepository.findImportTimeById(1L);
    }


    /**
     * Save a curConfig.
     *
     * @param curConfigDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CurConfigDTO save(CurConfigDTO curConfigDTO) {
        log.debug("Request to save CurConfig : {}", curConfigDTO);
        CurConfig curConfig = curConfigMapper.toEntity(curConfigDTO);
        curConfig = curConfigRepository.save(curConfig);
        return curConfigMapper.toDto(curConfig);
    }

    /**
     * Get all the curConfigs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CurConfigDTO> findAll() {
        log.debug("Request to get all CurConfigs");
        return curConfigRepository.findAll().stream()
            .map(curConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one curConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurConfigDTO> findOne(Long id) {
        log.debug("Request to get CurConfig : {}", id);
        return curConfigRepository.findById(id)
            .map(curConfigMapper::toDto);
    }

    @Override
    public void setMcfClient() {
        CurConfigDTO curConfigDTO = new CurConfigDTO();
        curConfigDTO.setCurrencyImportTime(derinfwBusService.getMcfClientCompanies(58559L).getBody().getCurrencyImportTime());
        save(curConfigDTO);
    }

    /**
     * Delete the curConfig by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurConfig : {}", id);
        curConfigRepository.deleteById(id);
    }
}
