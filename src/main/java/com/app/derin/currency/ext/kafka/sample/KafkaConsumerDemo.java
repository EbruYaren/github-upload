package com.app.derin.currency.ext.kafka.sample;

import java.time.LocalDate;

public class KafkaConsumerDemo {
    public static void main(String[] args) {
        SampleConsumer consumerThread = new SampleConsumer("testTopic");
        consumerThread.start();
    }
}
