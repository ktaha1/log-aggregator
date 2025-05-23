package com.tahakamil.kafka.logaggregator;

import com.tahakamil.kafka.logaggregator.config.properties.KafkaProperties;
import com.tahakamil.kafka.logaggregator.config.properties.KafkaPropertiesLoader;
import com.tahakamil.kafka.logaggregator.generator.FakeLogGenerator;
import com.tahakamil.kafka.logaggregator.generator.LogGenerator;
import com.tahakamil.kafka.logaggregator.publisher.KafkaLogPublisher;
import com.tahakamil.kafka.logaggregator.publisher.LogPublisher;
import com.tahakamil.kafka.logaggregator.service.LogService;

public class LogProducerMain {
    public static void main(String[] args) {
        KafkaProperties config = KafkaPropertiesLoader.loadDefault();
        try (LogPublisher publisher = new KafkaLogPublisher(config)) {
            LogGenerator generator = new FakeLogGenerator();
            new LogService(publisher, generator).publishBatch(100);
        }
    }
}
