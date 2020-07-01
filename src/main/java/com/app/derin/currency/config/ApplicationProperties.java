package com.app.derin.currency.config;

import com.app.derin.currency.repository.CurConfigRepository;
import com.app.derin.currency.service.CurConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Properties specific to Derincurrency.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {


    @Autowired
    private CurConfigRepository curConfigRepository;

    private final CurConfigService curConfigService;

    private final Logger log = LoggerFactory.getLogger(ApplicationProperties.class);

    public ApplicationProperties(CurConfigService curConfigService) {
        this.curConfigService = curConfigService;
    }


    @Bean(name = "getCron")
    public String getCronValue() {
        return curConfigRepository.findAll().get(0).getCurrencyImportTime();
    }

}
