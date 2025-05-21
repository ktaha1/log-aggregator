package com.tahakamil.kafka.logaggregator;

import com.tahakamil.kafka.logaggregator.config.KafkaProducerConfig;
import com.tahakamil.kafka.logaggregator.config.properties.KafkaProperties;
import com.tahakamil.kafka.logaggregator.config.properties.KafkaPropertiesLoader;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogProducer.class);

    public static void main(String[] args) {
        KafkaProperties config = KafkaPropertiesLoader.loadDefault();
        Producer<String, String> producer = new KafkaProducer<>(KafkaProducerConfig.build(config));



        LOGGER.info("Reading KafkaConfig Properties: {}", config);
    }
}
