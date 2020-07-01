package com.app.derin.currency.ext.kafka.alerts;

import com.app.derin.currency.config.KafkaProperties;
import com.app.derin.currency.ext.derinfw.dto.CurCurrenciesDTOExt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;

@Service
public class AlertService {
    private final Logger log = LoggerFactory.getLogger(AlertService.class);

    private static final String TOPIC = "cur_currency_manuel_add";

    private final KafkaProperties kafkaProperties;

    private final static Logger logger = LoggerFactory.getLogger(AlertService.class);
    private KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AlertService(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @PostConstruct
    public void initialize(){
        log.info("Kafka producer initializing...");
        this.producer = new KafkaProducer<String, String>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }

    public void alertCurCurrency(CurCurrenciesDTOExt curCurrenciesDTOExt) {
        try {
            initialize();

            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            String message = objectMapper.writeValueAsString(curCurrenciesDTOExt);
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
            log.debug("{}", record);
            producer.send(record);
        } catch (JsonProcessingException e) {
            logger.error("Could not send store alert", e);
            throw new AlertServiceException(e);
        }
    }

    @PreDestroy
    public String shutdown() {
        log.info("Shutdown Kafka producer");
        producer.close();
        return "CurCurrencyDate'e ekleme tamamlandı";
    }
}
