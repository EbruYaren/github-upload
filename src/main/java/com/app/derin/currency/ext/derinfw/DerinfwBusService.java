package com.app.derin.currency.ext.derinfw;

import com.app.derin.currency.ext.derinfw.dto.McfClientCompaniesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public interface DerinfwBusService {

    //@GetMapping(value = "/api/mcf-client-company/{id}")
    ResponseEntity<McfClientCompaniesDTO> getMcfClientCompanies(Long id);
}
