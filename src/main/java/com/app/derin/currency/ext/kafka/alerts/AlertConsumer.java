package com.app.derin.currency.ext.kafka.alerts;

import com.app.derin.currency.config.KafkaProperties;
import com.app.derin.currency.ext.derinfw.dto.CurCurrenciesDTOExt;
import com.app.derin.currency.service.CurCurrencyDateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AlertConsumer {
    private final Logger log = LoggerFactory.getLogger(AlertConsumer.class);

    private final AtomicBoolean closed = new AtomicBoolean(false);

    public static final String TOPIC = "cur_currency_manuel_add";

    private final KafkaProperties kafkaProperties;

    private KafkaConsumer<String, String> kafkaConsumer;

    private final CurCurrencyDateService curCurrencyDateService;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public String durum = "Devam eden işlem yok";

    public AlertConsumer(KafkaProperties kafkaProperties, CurCurrencyDateService curCurrencyDateService) {
        this.kafkaProperties = kafkaProperties;
        this.curCurrencyDateService = curCurrencyDateService;
    }


    @PostConstruct
    public void start() {
        log.info("Kafka consumer starting...");
        this.kafkaConsumer = new KafkaConsumer<>(kafkaProperties.getConsumerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        kafkaConsumer.subscribe(Collections.singletonList(TOPIC));
        log.info("Kafka consumer started");
        AtomicReference<String> message = new AtomicReference<>("İşlem devam ediyor");
        executorService.execute(() -> {
            try {

                while (!closed.get()) {
                    durum = "devam eden işlem bulunmaktadır";
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(3));
                    log.debug("{}",records);
                    for (ConsumerRecord<String, String> record : records) {
                        log.info("Consumed message in {} : {}", TOPIC, record.value());
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                        objectMapper.registerModule(new JavaTimeModule());
                        CurCurrenciesDTOExt curCurrenciesDTOExt = objectMapper.readValue(record.value(), CurCurrenciesDTOExt.class);
                        String code = curCurrenciesDTOExt.getCurrencyCode();
                        LocalDate startDate = curCurrenciesDTOExt.getStartDate();
                        LocalDate endDate = curCurrenciesDTOExt.getEndDate();
                        curCurrencyDateService.setJsonArrayAndSave(code, startDate, endDate);
                    }
                }

                kafkaConsumer.commitSync();

            } catch (WakeupException e) {
                // Ignore exception if closing
                if (!closed.get()) throw e;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                log.info("Kafka consumer close");
                durum = "Devam eden işlem yok";
                kafkaConsumer.close();
            }
        });
    }
    public KafkaConsumer<String, String> getKafkaConsumer() {
        return kafkaConsumer;
    }

    public void shutdown() {
        log.info("Shutdown Kafka consumer");
        closed.set(true);
        kafkaConsumer.wakeup();
    }
}
