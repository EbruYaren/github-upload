package com.app.derin.currency.web.rest;


import com.app.derin.currency.ext.derinfw.DerinfwBusService;
import com.app.derin.currency.ext.dto.QueryRequestDTO;
import com.app.derin.currency.ext.query.QueryBuilderBusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/query")
public class QueryBuilderResource {

    private final Logger log = LoggerFactory.getLogger(QueryBuilderResource.class);

    private static final String ENTITY_NAME = "QueryBuilderResource";

    private final QueryBuilderBusService queryBuilderBusService;

    private final DerinfwBusService derinfwBusService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public QueryBuilderResource(QueryBuilderBusService queryBuilderBusService, DerinfwBusService derinfwBusService) {
        this.queryBuilderBusService = queryBuilderBusService;
        this.derinfwBusService = derinfwBusService;
    }

    @GetMapping("/query-result")
    public ResponseEntity<?> getQueryBuilderResult(@RequestBody QueryRequestDTO queryFields) {
        log.debug("REST request to get sql query from json data");

        List<Map<String, Object>> result = queryBuilderBusService.getResult(queryFields.getStrQuery(),queryFields.getFields().stream().collect(Collectors.toList()));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/mcf-client-company/{id}")
    public ResponseEntity<?> getCompany(@PathVariable Long id)
    {
        return derinfwBusService.getMcfClientCompanies(id);
    }
}
