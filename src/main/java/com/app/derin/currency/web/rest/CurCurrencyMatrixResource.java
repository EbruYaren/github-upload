package com.app.derin.currency.web.rest;

import com.app.derin.currency.ext.dto.ResultDTO;
import com.app.derin.currency.ext.errors.MessageTranslator;
import com.app.derin.currency.service.CurCurrencyMatrixService;
import com.app.derin.currency.web.rest.errors.BadRequestAlertException;
import com.app.derin.currency.service.dto.CurCurrencyMatrixDTO;

import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.derin.currency.domain.CurCurrencyMatrix}.
 */
@RestController
@RequestMapping("/api")
public class CurCurrencyMatrixResource {

    private final Logger log = LoggerFactory.getLogger(CurCurrencyMatrixResource.class);

    private static final String ENTITY_NAME = "derincurrencyCurCurrencyMatrix";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurCurrencyMatrixService curCurrencyMatrixService;

    private final MessageTranslator messageTranslator;

    public CurCurrencyMatrixResource(CurCurrencyMatrixService curCurrencyMatrixService, MessageTranslator messageTranslator) {
        this.curCurrencyMatrixService = curCurrencyMatrixService;
        this.messageTranslator = messageTranslator;
    }

    /**
     * {@code POST  /cur-currency-matrices} : Create a new curCurrencyMatrix.
     *
     * @param curCurrencyMatrixDTO the curCurrencyMatrixDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curCurrencyMatrixDTO, or with status {@code 400 (Bad Request)} if the curCurrencyMatrix has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cur-currency-matrix")
    public ResponseEntity<ResultDTO> createCurCurrencyMatrix(@Valid @RequestBody CurCurrencyMatrixDTO curCurrencyMatrixDTO) throws URISyntaxException {
        log.debug("REST request to save CurCurrencyMatrix : {}", curCurrencyMatrixDTO);
        if (curCurrencyMatrixDTO.getId() != null) {
            return messageTranslator.getResponseEntity("cur_currency_date_id", "MustNull", ENTITY_NAME, false, null);
        }
        CurCurrencyMatrixDTO result = curCurrencyMatrixService.save(curCurrencyMatrixDTO);
        ResultDTO resultDTO = messageTranslator.getResult("cur_currency_date_id", "CreateSuccess", ENTITY_NAME, true, result);
        return ResponseEntity.created(new URI("/api/cur-currency-matrix/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(resultDTO);
    }

    /**
     * {@code PUT  /cur-currency-matrices} : Updates an existing curCurrencyMatrix.
     *
     * @param curCurrencyMatrixDTO the curCurrencyMatrixDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated curCurrencyMatrixDTO,
     * or with status {@code 400 (Bad Request)} if the curCurrencyMatrixDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the curCurrencyMatrixDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cur-currency-matrix")
    public ResponseEntity<ResultDTO> updateCurCurrencyMatrix(@Valid @RequestBody CurCurrencyMatrixDTO curCurrencyMatrixDTO) throws URISyntaxException {
        log.debug("REST request to update CurCurrencyMatrix : {}", curCurrencyMatrixDTO);
        if (curCurrencyMatrixDTO.getId() == null) {
            return messageTranslator.getResponseEntity("cur_currency_date_id", "NotNull", ENTITY_NAME, false, null);
    }
        CurCurrencyMatrixDTO result = curCurrencyMatrixService.save(curCurrencyMatrixDTO);
        ResultDTO resultDTO = messageTranslator.getResult("cur_currency_date_id", "UpdateSuccess", ENTITY_NAME, true, result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, curCurrencyMatrixDTO.getId().toString()))
            .body(resultDTO);
    }

    /**
     * {@code GET  /cur-currency-matrices} : get all the curCurrencyMatrices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of curCurrencyMatrices in body.
     */
    @GetMapping("/cur-currency-matrices")
    public ResultDTO getAllCurCurrencyMatrices() {
        log.debug("REST request to get all CurCurrencyMatrices");
        ResultDTO resultDTO = new ResultDTO(true, curCurrencyMatrixService.findAll(), "CurCurrencyMatrix list");
        return resultDTO;
    }

    /**
     * {@code GET  /cur-currency-matrices/:id} : get the "id" curCurrencyMatrix.
     *
     * @param id the id of the curCurrencyMatrixDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the curCurrencyMatrixDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cur-currency-matrix/{id}")
    public ResponseEntity<ResultDTO> getCurCurrencyMatrix(@PathVariable Long id) {
        log.debug("REST request to get CurCurrencyMatrix : {}", id);
        ResultDTO resultDTO = new ResultDTO();
        Optional<CurCurrencyMatrixDTO> curCurrencyMatrixDTO = curCurrencyMatrixService.findOne(id);
        if(curCurrencyMatrixDTO.isPresent()) {
            resultDTO.success = true;
            resultDTO.message = "CurCurrencyMatrix by id";
            resultDTO.data = curCurrencyMatrixDTO.get();
        }
        else{
            return messageTranslator.getResponseEntity("cur_currency_matrix_id", "NotFound", ENTITY_NAME, false, null);
        }
        return ResponseEntity.ok().body(resultDTO);
    }

    /**
     * {@code DELETE  /cur-currency-matrices/:id} : delete the "id" curCurrencyMatrix.
     *
     * @param id the id of the curCurrencyMatrixDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cur-currency-matrix/{id}")
    public ResponseEntity<ResultDTO> deleteCurCurrencyMatrix(@PathVariable Long id) {
        log.debug("REST request to delete CurCurrencyMatrix : {}", id);
        curCurrencyMatrixService.delete(id);
        ResultDTO resultDTO = messageTranslator.getResult("cur_currency_matrix_id", "DeleteSuccess", ENTITY_NAME, true, null);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).body(resultDTO);
    }
}
