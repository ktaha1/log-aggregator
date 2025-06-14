package com.tahakamil.kafka.logaggregator.publisher.config;

import com.tahakamil.kafka.logaggregator.publisher.config.properties.KafkaProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerConfig {
    public static Properties build(KafkaProperties kafkaProps) {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getBootstrapServers());
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Connection retry settings
        props.setProperty(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "1000");
        props.setProperty(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, "1000");
        props.setProperty(ProducerConfig.MAX_BLOCK_MS_CONFIG, "120000");

        // Add more robust connection settings
        props.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
        props.setProperty(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "120000");
        props.setProperty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, "30000");
        props.setProperty(ProducerConfig.ACKS_CONFIG, "1");
        props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "false");

        // Add client ID for better tracking
        props.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "log-publisher");

        return props;
    }
}
