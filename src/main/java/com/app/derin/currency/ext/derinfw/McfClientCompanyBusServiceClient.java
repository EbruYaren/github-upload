package com.app.derin.currency.ext.derinfw;

import com.app.derin.currency.client.AuthorizedFeignClient;
import com.app.derin.currency.ext.derinfw.dto.McfClientCompaniesDTO;
import com.app.derin.currency.ext.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@AuthorizedFeignClient(name = "derinconfiguration", decode404 = true)
@RequestMapping("/api")
public interface McfClientCompanyBusServiceClient {

    @GetMapping(value = "/mcf-client-company/{id}")
    ResponseEntity<ResultDTO> getMcfClientCompany(@PathVariable("id") Long id);

}
