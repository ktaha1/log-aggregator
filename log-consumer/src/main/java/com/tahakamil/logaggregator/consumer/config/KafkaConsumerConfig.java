package com.tahakamil.logaggregator.consumer.config;

import com.tahakamil.logaggregator.consumer.config.properties.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public class KafkaConsumerConfig {
    public static Properties build(KafkaProperties kafkaProps) {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getBootstrapServers());
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        
        // Consumer group configuration
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, kafkaProps.getGroupId());
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        // Connection retry settings
        props.setProperty(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, "30000");
        props.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "3000");
        
        // Enable auto commit for simplicity
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        
        // Add client ID for better tracking
        props.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "log-consumer");
        
        return props;
    }
}