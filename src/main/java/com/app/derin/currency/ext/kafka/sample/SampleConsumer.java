package com.app.derin.currency.ext.kafka.sample;

import com.app.derin.currency.service.CurCurrencyDateService;
import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Properties;
public class SampleConsumer extends ShutdownableThread  {
    private final Logger log = LoggerFactory.getLogger(SampleConsumer.class);

    private final KafkaConsumer<Integer, String> consumer;
    private final String topic;

    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final String CLIENT_ID = "SampleConsumer";

    private CurCurrencyDateService curCurrencyDateService;
    private String currencyCode;
    private LocalDate startDate;
    private LocalDate endDate;

    public SampleConsumer(String topic) {
        super("KafkaConsumerExample", false);
        this.currencyCode = currencyCode;
        this.startDate = startDate;
        this.endDate = endDate;
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CLIENT_ID);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer(props);
        this.topic = topic;
    }

    @Override
    public void doWork() {
        log.debug("Consumer Thread started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        consumer.subscribe(Collections.singletonList(this.topic));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        log.debug("{}", records);
        for (ConsumerRecord<Integer, String> record : records) {
//            try {
//                curCurrencyDateService.setJsonArrayAndSave(currencyCode, startDate, endDate);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            System.out.println("Received message: ----------------------- (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
        }
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public boolean isInterruptible() {
        return false;
    }

}
