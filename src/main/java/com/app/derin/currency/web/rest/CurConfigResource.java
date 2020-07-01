package com.app.derin.currency.web.rest;

import com.app.derin.currency.ext.dto.ResultDTO;
import com.app.derin.currency.ext.errors.MessageTranslator;
import com.app.derin.currency.service.CurConfigService;
import com.app.derin.currency.web.rest.errors.BadRequestAlertException;
import com.app.derin.currency.service.dto.CurConfigDTO;

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
 * REST controller for managing {@link com.app.derin.currency.domain.CurConfig}.
 */
@RestController
@RequestMapping("/api")
public class CurConfigResource {

    private final Logger log = LoggerFactory.getLogger(CurConfigResource.class);

    private static final String ENTITY_NAME = "derincurrencyCurConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurConfigService curConfigService;

    private final MessageTranslator messageTranslator;

    public CurConfigResource(CurConfigService curConfigService, MessageTranslator messageTranslator) {
        this.curConfigService = curConfigService;
        this.messageTranslator = messageTranslator;
    }

    /**
     * {@code POST  /cur-configs} : Create a new curConfig.
     *
     * @param curConfigDTO the curConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curConfigDTO, or with status {@code 400 (Bad Request)} if the curConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cur-config")
    public ResponseEntity<ResultDTO> createCurConfig(@Valid @RequestBody CurConfigDTO curConfigDTO) throws URISyntaxException {
        log.debug("REST request to save CurConfig : {}", curConfigDTO);
        if (curConfigDTO.getId() != null) {
            return messageTranslator.getResponseEntity("cur_config_id", "MustNull", ENTITY_NAME, false, null);
        }
        CurConfigDTO result = curConfigService.save(curConfigDTO);
        ResultDTO resultDTO = messageTranslator.getResult("cur_config_id", "CreateSuccess", ENTITY_NAME, true, result);
          return ResponseEntity.created(new URI("/api/cur-config/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(resultDTO);
    }

    /**
     * {@code PUT  /cur-configs} : Updates an existing curConfig.
     *
     * @param curConfigDTO the curConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated curConfigDTO,
     * or with status {@code 400 (Bad Request)} if the curConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the curConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PutMapping("/cur-config")
    public ResponseEntity<ResultDTO> updateCurConfig(@Valid @RequestBody CurConfigDTO curConfigDTO) throws URISyntaxException {
        String str = "Cron updated";
        log.debug("REST request to update CurConfig : {}", curConfigDTO);
        if (curConfigDTO.getId() == null) {
            return messageTranslator.getResponseEntity("cur_config_id", "NotNull", ENTITY_NAME, false, null);
        }
        CurConfigDTO result = curConfigService.save(curConfigDTO);
        ResultDTO resultDTO = messageTranslator.getResult("cur_config_id", "UpdateSuccess", ENTITY_NAME, true, result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, curConfigDTO.getId().toString()))
            .body(resultDTO);
    }

    /**
     * {@code GET  /cur-configs} : get all the curConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of curConfigs in body.
     */
    @GetMapping("/cur-configs")
    public ResultDTO getAllCurConfigs() {
        log.debug("REST request to get all CurConfigs");
        ResultDTO resultDTO = new ResultDTO(true, curConfigService.findAll(), "CurConfig list");
        return resultDTO;
    }

    /**
     * {@code GET  /cur-configs/:id} : get the "id" curConfig.
     *
     * @param id the id of the curConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the curConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cur-configs/{id}")
    public ResponseEntity<ResultDTO> getCurConfig(@PathVariable Long id) {
        log.debug("REST request to get CurConfig : {}", id);
        ResultDTO resultDTO = new ResultDTO();
        Optional<CurConfigDTO> curConfigDTO = curConfigService.findOne(id);
        if(curConfigDTO.isPresent()){
            resultDTO.success = true;
            resultDTO.message = "CurConfig by id";
            resultDTO.data = curConfigDTO.get();
        }
        else{
            return messageTranslator.getResponseEntity("cur_config_id", "NotFound", ENTITY_NAME, false, null);
        }
        return ResponseEntity.ok().body(resultDTO);
    }

    /**
     * {@code DELETE  /cur-configs/:id} : delete the "id" curConfig.
     *
     * @param id the id of the curConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cur-configs/{id}")
    public ResponseEntity<ResultDTO> deleteCurConfig(@PathVariable Long id) {
        log.debug("REST request to delete CurConfig : {}", id);
        curConfigService.delete(id);
        ResultDTO resultDTO = messageTranslator.getResult("cur_config_id", "DeleteSuccess", ENTITY_NAME, true, null);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).body(resultDTO);
    }
}
