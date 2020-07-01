package com.app.derin.currency.web.rest;

import com.app.derin.currency.ext.dto.ResultDTO;
import com.app.derin.currency.ext.errors.MessageTranslator;
import com.app.derin.currency.service.CurCurrencyDateService;
import com.app.derin.currency.web.rest.errors.BadRequestAlertException;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.dto.CurCurrencyDateCriteria;
import com.app.derin.currency.service.CurCurrencyDateQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.derin.currency.domain.CurCurrencyDate}.
 */
@RestController
@RequestMapping("/api")
public class CurCurrencyDateResource {

    private final Logger log = LoggerFactory.getLogger(CurCurrencyDateResource.class);

    private static final String ENTITY_NAME = "derincurrencyCurCurrencyDate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurCurrencyDateService curCurrencyDateService;

    private final CurCurrencyDateQueryService curCurrencyDateQueryService;

    private final MessageTranslator messageTranslator;

    public CurCurrencyDateResource(CurCurrencyDateService curCurrencyDateService, CurCurrencyDateQueryService curCurrencyDateQueryService, MessageTranslator messageTranslator) {
        this.curCurrencyDateService = curCurrencyDateService;
        this.curCurrencyDateQueryService = curCurrencyDateQueryService;
        this.messageTranslator = messageTranslator;
    }

    /**
     * {@code POST  /cur-currency-dates} : Create a new curCurrencyDate.
     *
     * @param curCurrencyDateDTO the curCurrencyDateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curCurrencyDateDTO, or with status {@code 400 (Bad Request)} if the curCurrencyDate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cur-currency-date")
    public ResponseEntity<ResultDTO> createCurCurrencyDate(@Valid @RequestBody CurCurrencyDateDTO curCurrencyDateDTO) throws URISyntaxException {
        log.debug("REST request to save CurCurrencyDate : {}", curCurrencyDateDTO);
        if (curCurrencyDateDTO.getId() != null) {
            return messageTranslator.getResponseEntity("cur_currency_date_id", "MustNull", ENTITY_NAME, false, null);
        }
        CurCurrencyDateDTO result = curCurrencyDateService.save(curCurrencyDateDTO);
        ResultDTO resultDTO = messageTranslator.getResult("cur_currency_date_id", "CreateSuccess", ENTITY_NAME, true, result);
        return ResponseEntity.created(new URI("/api/cur-currency-date/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(resultDTO);
    }

    /**
     * {@code PUT  /cur-currency-dates} : Updates an existing curCurrencyDate.
     *
     * @param curCurrencyDateDTO the curCurrencyDateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated curCurrencyDateDTO,
     * or with status {@code 400 (Bad Request)} if the curCurrencyDateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the curCurrencyDateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cur-currency-date")
    public ResponseEntity<ResultDTO> updateCurCurrencyDate(@Valid @RequestBody CurCurrencyDateDTO curCurrencyDateDTO) throws URISyntaxException {
        log.debug("REST request to update CurCurrencyDate : {}", curCurrencyDateDTO);
        if (curCurrencyDateDTO.getId() == null) {
            return messageTranslator.getResponseEntity("cur_currency_date_id", "NotNull", ENTITY_NAME, false, null);
        }
        CurCurrencyDateDTO result = curCurrencyDateService.save(curCurrencyDateDTO);
        ResultDTO resultDTO = messageTranslator.getResult("cur_currency_date_id", "UpdateSuccess", ENTITY_NAME, true, result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, curCurrencyDateDTO.getId().toString()))
            .body(resultDTO);
    }

    /**
     * {@code GET  /cur-currency-dates} : get all the curCurrencyDates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of curCurrencyDates in body.
     */
    @GetMapping("/cur-currency-dates")
    public ResponseEntity<ResultDTO> getAllCurCurrencyDates(CurCurrencyDateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CurCurrencyDates by criteria: {}", criteria);
        Page<CurCurrencyDateDTO> page = curCurrencyDateQueryService.findByCriteria(criteria, pageable);
        ResultDTO resultDTO = new ResultDTO(true, page.getContent(), "CurCurrencyDate list by criteria");
        return ResponseEntity.ok().body(resultDTO);
    }

    /**
     * {@code GET  /cur-currency-dates/count} : count all the curCurrencyDates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cur-currency-dates/count")
    public ResponseEntity<ResultDTO> countCurCurrencyDates(CurCurrencyDateCriteria criteria) {
        log.debug("REST request to count CurCurrencyDates by criteria: {}", criteria);
        ResultDTO resultDTO = new ResultDTO(true, curCurrencyDateQueryService.countByCriteria(criteria), "CurCurrencyDate count by criteria");
        return ResponseEntity.ok().body(resultDTO);
    }

    /**
     * {@code GET  /cur-currency-dates/:id} : get the "id" curCurrencyDate.
     *
     * @param id the id of the curCurrencyDateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the curCurrencyDateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cur-currency-date/{id}")
    public ResponseEntity<ResultDTO> getCurCurrencyDate(@PathVariable Long id) {
        log.debug("REST request to get CurCurrencyDate : {}", id);
        ResultDTO resultDTO = new ResultDTO();
        Optional<CurCurrencyDateDTO> curCurrencyDateDTO = curCurrencyDateService.findOne(id);
        if(curCurrencyDateDTO.isPresent()) {
            resultDTO.success = true;
            resultDTO.message = "CurCurrencyDate by id";
            resultDTO.data = curCurrencyDateDTO.get();
        }
        else{
            return messageTranslator.getResponseEntity("cur_currency_date_id", "NotFound", ENTITY_NAME, false, null);
        }
        return ResponseEntity.ok().body(resultDTO);
    }

    /**
     * {@code DELETE  /cur-currency-dates/:id} : delete the "id" curCurrencyDate.
     *
     * @param id the id of the curCurrencyDateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cur-currency-dates/{id}")
    public ResponseEntity<ResultDTO> deleteCurCurrencyDate(@PathVariable Long id) {
        log.debug("REST request to delete CurCurrencyDate : {}", id);
        curCurrencyDateService.delete(id);
        ResultDTO resultDTO = messageTranslator.getResult("cur_currency_date_id", "DeleteSuccess", ENTITY_NAME, true, null);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).body(resultDTO);
    }
}
