package com.app.derin.currency.web.rest;

import com.app.derin.currency.ext.derinfw.McfClientCompanyBusServiceClient;
import com.app.derin.currency.ext.derinfw.dto.CurCurrenciesDTOExt;
import com.app.derin.currency.ext.derinuaa.UaaBusServiceClient;
import com.app.derin.currency.ext.derinuaa.UaaServiceClient;
import com.app.derin.currency.ext.dto.ResultDTO;
import com.app.derin.currency.ext.errors.MessageTranslator;
import com.app.derin.currency.ext.kafka.alerts.AlertConsumer;
import com.app.derin.currency.ext.kafka.alerts.AlertService;
import com.app.derin.currency.security.SecurityUtils;
import com.app.derin.currency.service.CurCurrenciesService;
import com.app.derin.currency.service.CurCurrencyDateService;
import com.app.derin.currency.web.rest.errors.BadRequestAlertException;
import com.app.derin.currency.service.dto.CurCurrenciesDTO;

import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.derin.currency.domain.CurCurrencies}.
 */
@RestController
@RequestMapping("/api")
public class CurCurrenciesResource {

    private final Logger log = LoggerFactory.getLogger(CurCurrenciesResource.class);

    private static final String ENTITY_NAME = "derincurrencyCurCurrencies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurCurrenciesService curCurrenciesService;

    private final CurCurrencyDateService curCurrencyDateService;

    private final AlertService alertService;

    private final AlertConsumer alertConsumer;

    private final UaaBusServiceClient uaaBusServiceClient;

    private final McfClientCompanyBusServiceClient mcfClientCompanyBusServiceClient;

    private final UaaServiceClient serviceClient;

    private final MessageTranslator messageTranslator;

    public CurCurrenciesResource(CurCurrenciesService curCurrenciesService, CurCurrencyDateService curCurrencyDateService, AlertService alertService, AlertConsumer alertConsumer, UaaBusServiceClient uaaBusServiceClient, McfClientCompanyBusServiceClient mcfClientCompanyBusServiceClient, UaaServiceClient serviceClient, MessageTranslator messageTranslator) {
        this.curCurrenciesService = curCurrenciesService;
        this.curCurrencyDateService = curCurrencyDateService;

        this.alertService = alertService;
        this.alertConsumer = alertConsumer;
        this.uaaBusServiceClient = uaaBusServiceClient;
        this.mcfClientCompanyBusServiceClient = mcfClientCompanyBusServiceClient;
        this.serviceClient = serviceClient;
        this.messageTranslator = messageTranslator;
    }


    /**
     * {@code POST  /cur-currencies} : Create a new curCurrencies.s
     *
     * @param curCurrenciesDTO the curCurrenciesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curCurrenciesDTO, or with status {@code 400 (Bad Request)} if the curCurrencies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cur-currency")
    public ResponseEntity<ResultDTO> createCurCurrency(@Valid @RequestBody CurCurrenciesDTO curCurrenciesDTO) throws URISyntaxException, IOException, ParseException {
        log.debug("REST request to save CurCurrencies : {}", curCurrenciesDTO);
        if (curCurrenciesDTO.getId() != null) {
            return messageTranslator.getResponseEntity("cur_currencies_id", "MustNull", ENTITY_NAME, false, null);
        }
        CurCurrenciesDTO result = curCurrenciesService.save(curCurrenciesDTO);
        ResultDTO resultDTO = messageTranslator.getResult("cur_currencies_id", "CreateSuccess", ENTITY_NAME, true, result);
        return ResponseEntity.created(new URI("/api/cur-currency/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(resultDTO);
    }
    /**
     * {@code PUT  /cur-currencies} : Update curCurrency with start date and end date
     *
     * @param curCurrenciesDTOExt the curCurrenciesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curCurrenciesDTO, or with status {@code 400 (Bad Request)} if the curCurrencies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cur-currency-with-start-date-and-end-date")
    public ResponseEntity<ResultDTO> updateCurrencyWithStartDateAndEndDate(@RequestBody CurCurrenciesDTOExt curCurrenciesDTOExt) throws URISyntaxException, IOException, ParseException {
        log.debug("REST request to save CurCurrencies with start date and end date : {}", curCurrenciesDTOExt);
        if (curCurrenciesDTOExt.getId() == null) {
            return messageTranslator.getResponseEntity("cur_currencies_id", "NotNull", ENTITY_NAME, false, null);
        }
         CurCurrenciesDTO result = curCurrenciesService.save(curCurrenciesDTOExt);
       // curCurrencyDateService.setJsonArrayAndSave(curCurrenciesDTOExt.getCurrencyCode(), curCurrenciesDTOExt.getStartDate(), curCurrenciesDTOExt.getEndDate());
        alertService.alertCurCurrency(curCurrenciesDTOExt);
        alertConsumer.start();
        ResultDTO resultDTO = new ResultDTO(true, curCurrenciesDTOExt, alertConsumer.durum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, curCurrenciesDTOExt.getId().toString()))
            .body(resultDTO);
    }

    @GetMapping("/cur-currency-continue-jobs")
    public ResponseEntity<ResultDTO> getResult() {
        ResultDTO resultDTO = new ResultDTO(true, null, alertConsumer.durum);
        return ResponseEntity.ok()
            .body(resultDTO);
    }


    /**
     * {@code PUT  /cur-currencies} : Updates an existing curCurrencies.
     *
     * @param curCurrenciesDTO the curCurrenciesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated curCurrenciesDTO,
     * or with status {@code 400 (Bad Request)} if the curCurrenciesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the curCurrenciesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cur-currency")
    public ResponseEntity<ResultDTO> updateCurCurrency(@RequestBody CurCurrenciesDTO curCurrenciesDTO) throws URISyntaxException {
        log.debug("REST request to update CurCurrency : {}", curCurrenciesDTO);
        if (curCurrenciesDTO.getId() == null) {
            return messageTranslator.getResponseEntity("cur_currencies.id", "NotNull", ENTITY_NAME, false, null);
        }
        CurCurrenciesDTO result = curCurrenciesService.save(curCurrenciesDTO);
        ResultDTO resultDTO = messageTranslator.getResult("cur_currencies.id", "UpdateSuccess", ENTITY_NAME, true, result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, curCurrenciesDTO.getId().toString()))
            .body(resultDTO);
    }

    /**
     * {@code GET  /cur-currencies} : get all the curCurrencies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of curCurrencies in body.
     */
    @GetMapping("/cur-currencies")
    public ResponseEntity<ResultDTO> getAllCurCurrencies() {
        log.debug("REST request to get all CurCurrencies");
        ResultDTO result = new ResultDTO(true, curCurrenciesService.findAll(), "CurCurrency list");
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /cur-currencies/:id} : get the "id" curCurrencies.
     *
     * @param id the id of the curCurrenciesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the curCurrenciesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cur-currency/{id}")
    public ResponseEntity<ResultDTO> getCurCurrency(@PathVariable Long id) {
        log.debug("REST request to get CurCurrency : {}", id);
        ResultDTO result = new ResultDTO();
        Optional<CurCurrenciesDTO> curCurrenciesDTO = curCurrenciesService.findOne(id);
        if(curCurrenciesDTO.isPresent()) {
            result.success = true;
            result.message = "CurCurrency by id";
            result.data = curCurrenciesDTO.get();
        }
        else{
            return messageTranslator.getResponseEntity("cur_currencies_id", "NotFound", ENTITY_NAME, false, null);
        }
        return ResponseEntity.ok().body(result);
    }


    /**
     * {@code DELETE  /cur-currencies/:id} : delete the "id" curCurrencies.
     *
     * @param id the id of the curCurrenciesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cur-currency/{id}")
    public ResponseEntity<ResultDTO> deleteCurCurrency(@PathVariable Long id) {
        log.debug("REST request to delete CurCurrency : {}", id);
        curCurrenciesService.delete(id);
        ResultDTO resultDTO = messageTranslator.getResult("cur_currencies_id", "DeleteSuccess", ENTITY_NAME, true, null);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).body(resultDTO);
    }
}
