package com.tahakamil.kafka.logaggregator.publisher.app;

import com.tahakamil.kafka.logaggregator.common.interfaces.LogGenerator;
import com.tahakamil.kafka.logaggregator.common.interfaces.LogPublisher;
import com.tahakamil.kafka.logaggregator.publisher.config.properties.KafkaProperties;
import com.tahakamil.kafka.logaggregator.publisher.config.properties.KafkaPropertiesLoader;
import com.tahakamil.kafka.logaggregator.publisher.generator.FakeLogGenerator;
import com.tahakamil.kafka.logaggregator.publisher.impl.KafkaLogPublisher;
import com.tahakamil.kafka.logaggregator.publisher.service.LogService;

public class LogProducerTest {
    private static void main(String[] args) {
        KafkaProperties config = KafkaPropertiesLoader.loadDefault();
        try (LogPublisher publisher = new KafkaLogPublisher(config)) {
            LogGenerator generator = new FakeLogGenerator();
            new LogService(publisher, generator).publishBatch(8);
        }
    }
}
