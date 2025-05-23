package com.tahakamil.kafka.logaggregator.publisher;

import com.tahakamil.kafka.logaggregator.config.KafkaProducerConfig;
import com.tahakamil.kafka.logaggregator.config.properties.KafkaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaLogPublisher implements LogPublisher {
    private static final Logger logger = LoggerFactory.getLogger(KafkaLogPublisher.class);
    private final Producer<String, String> producer;
    private final String topic;

    public KafkaLogPublisher(KafkaProperties config) {
        this.producer = new KafkaProducer<>(KafkaProducerConfig.build(config));
        this.topic = config.getTopic();
    }

    @Override
    public void publish(String key, String message) {
        producer.send(new ProducerRecord<>(topic, key, message), (metadata, exception) -> {
            if (exception == null) {
                logger.info("Log message Sent to topic: {} -- message: [{}] {} -- (partition={}, offset={})",
                        topic, key, message, metadata.partition(), metadata.offset());
            } else {
                logger.error("Failed to send log message", exception);
            }
        });
    }

    @Override
    public void close() {
        producer.flush();
        producer.close();
    }
}
