package com.app.derin.currency.ext.derinfw;

import com.app.derin.currency.ext.derinfw.dto.McfClientCompaniesDTO;
import com.app.derin.currency.ext.dto.ResultDTO;
import com.app.derin.currency.security.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DerinfwBusServiceImpl implements DerinfwBusService {

    @Override
    public ResponseEntity<McfClientCompaniesDTO> getMcfClientCompanies(Long id) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/mcf-client-company/"+id;

        ResultDTO resultDTO  = restTemplate.getForEntity(url, ResultDTO.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        McfClientCompaniesDTO companiesDTO = objectMapper.convertValue(resultDTO.data,McfClientCompaniesDTO.class);
        return ResponseEntity.ok ().body(companiesDTO);
    }
}
