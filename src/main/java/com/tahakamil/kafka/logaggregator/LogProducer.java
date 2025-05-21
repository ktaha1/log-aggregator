package com.tahakamil.kafka.logaggregator;

import com.tahakamil.kafka.logaggregator.config.KafkaProducerConfig;
import com.tahakamil.kafka.logaggregator.config.properties.KafkaProperties;
import com.tahakamil.kafka.logaggregator.config.properties.KafkaPropertiesLoader;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogProducer {
    private static final Logger logger = LoggerFactory.getLogger(LogProducer.class);

    public static void main(String[] args) {
        KafkaProperties config = KafkaPropertiesLoader.loadDefault();
        String topic = config.getTopic();

        // Create Kafka producer
        logger.info("Starting LogProducer to topic '{}'", topic);

        try (Producer<String, String> producer = new KafkaProducer<>(KafkaProducerConfig.build(config))) {
            for (int i = 1; i <= 10; i++) {
                String key = "service-A";
                String value = String.format("Log message #%d from %s V2", i, key);

                ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

                producer.send(record, (metadata, exception) -> {
                    if (exception == null) {
                        logger.info("Sent: [{}] -> {} | partition={} offset={}",
                                key, value, metadata.partition(), metadata.offset());
                    } else {
                        logger.error("Failed to send log message", exception);
                    }
                });
            }

            producer.flush();
        }

        logger.info("LogProducer finished and shut down.");


    }
}
