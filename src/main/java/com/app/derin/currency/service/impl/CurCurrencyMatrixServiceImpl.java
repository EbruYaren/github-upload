package com.app.derin.currency.service.impl;

import com.app.derin.currency.domain.CurCurrencies;
import com.app.derin.currency.ext.mapper.CurCurrencyMatrixMapperExt;
import com.app.derin.currency.service.CurCurrencyMatrixService;
import com.app.derin.currency.domain.CurCurrencyMatrix;
import com.app.derin.currency.repository.CurCurrencyMatrixRepository;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.dto.CurCurrencyMatrixDTO;
import com.app.derin.currency.service.mapper.CurCurrencyMatrixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CurCurrencyMatrix}.
 */
@Service
@Transactional
public class CurCurrencyMatrixServiceImpl implements CurCurrencyMatrixService {

    private final Logger log = LoggerFactory.getLogger(CurCurrencyMatrixServiceImpl.class);

    private final CurCurrencyMatrixRepository curCurrencyMatrixRepository;

    private final CurCurrencyMatrixMapper curCurrencyMatrixMapper;

    private final CurCurrencyMatrixMapperExt curCurrencyMatrixMapperExt;

    public CurCurrencyMatrixServiceImpl(CurCurrencyMatrixRepository curCurrencyMatrixRepository, CurCurrencyMatrixMapper curCurrencyMatrixMapper, CurCurrencyMatrixMapperExt curCurrencyMatrixMapperExt) {
        this.curCurrencyMatrixRepository = curCurrencyMatrixRepository;
        this.curCurrencyMatrixMapper = curCurrencyMatrixMapper;
        this.curCurrencyMatrixMapperExt = curCurrencyMatrixMapperExt;
    }
    /**
     * Update curCurrencyMatrix.
     *
     * @param curCurrencyDateDTO the entity to update.
     */
    //Günlük kurların CurCurrencyDate'e eklenmesiyle CurCurrencyMatrix tablosunun güncellenmesi
    //CurCurrencyDate servisinde [saveRates] ve [insertToCurCurrencyDate] methodlarından gelen curCurrencyDTO'ya göre
    @Override
    public void updateTable(CurCurrencyDateDTO curCurrencyDateDTO) {
        //Karşılaştırma için gelen Dto'un source ve result currency id'leri tutuluyor
        Long inSourceId = curCurrencyDateDTO.getSourceCurrencyId();
        Long inResultId = curCurrencyDateDTO.getResultCurrencyId();
        //CurCurrencyMatrix tablosundaki veri gelen CurCurrencyDateDTO'nun source ve result currency id'lerine göre getiriliyor
        CurCurrencyMatrix curCurrencyMatrix = curCurrencyMatrixRepository.findBySourceCurrencyAndResultCurrency(inSourceId, inResultId);
        //Eşleşme olmamasında gelen CurCurrencyDateDTO'nun yeni bir CurCurrencyMatrixDTO'su oluşuyor ve kaydediliyor.
        if (curCurrencyMatrix == null) {
            CurCurrencyMatrixDTO curCurrencyMatrixDTO = curCurrencyMatrixMapperExt.dateDtoToMatrixDto(curCurrencyDateDTO);
            curCurrencyMatrixRepository.save(curCurrencyMatrixMapper.toEntity(curCurrencyMatrixDTO));
        }
        //Eşleşme olması durumunda ise eşleşen CurCurrencyMatrix verisi gelen CurCurrencyDateDTO'ya göre güncelleniyor
        else {
            CurCurrencies sourceCurrency = curCurrencyMatrix.getSourceCurrency();
            CurCurrencies resultCurrency = curCurrencyMatrix.getResultCurrency();
            Long sourceId = sourceCurrency.getId();
            Long resultId = resultCurrency.getId();
            if (inSourceId.equals(sourceId) && inResultId.equals(resultId)) {
                CurCurrencyMatrixDTO curCurrencyMatrixDTO = curCurrencyMatrixMapper.toDto(curCurrencyMatrix);
                curCurrencyMatrixDTO = curCurrencyMatrixMapperExt.setMatrixDto(curCurrencyMatrixDTO, curCurrencyDateDTO);
                curCurrencyMatrixRepository.save(curCurrencyMatrixMapper.toEntity(curCurrencyMatrixDTO));
            }

        }
    }

    /**
     * Save a curCurrencyMatrix.
     *
     * @param curCurrencyMatrixDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CurCurrencyMatrixDTO save(CurCurrencyMatrixDTO curCurrencyMatrixDTO) {
        log.debug("Request to save CurCurrencyMatrix : {}", curCurrencyMatrixDTO);
        CurCurrencyMatrix curCurrencyMatrix = curCurrencyMatrixMapper.toEntity(curCurrencyMatrixDTO);
        curCurrencyMatrix = curCurrencyMatrixRepository.save(curCurrencyMatrix);
        return curCurrencyMatrixMapper.toDto(curCurrencyMatrix);
    }

    /**
     * Get all the curCurrencyMatrices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CurCurrencyMatrixDTO> findAll() {
        log.debug("Request to get all CurCurrencyMatrices");
        return curCurrencyMatrixRepository.findAll().stream()
            .map(curCurrencyMatrixMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one curCurrencyMatrix by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurCurrencyMatrixDTO> findOne(Long id) {
        log.debug("Request to get CurCurrencyMatrix : {}", id);
        return curCurrencyMatrixRepository.findById(id)
            .map(curCurrencyMatrixMapper::toDto);
    }

    /**
     * Delete the curCurrencyMatrix by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurCurrencyMatrix : {}", id);
        curCurrencyMatrixRepository.deleteById(id);
    }


}
