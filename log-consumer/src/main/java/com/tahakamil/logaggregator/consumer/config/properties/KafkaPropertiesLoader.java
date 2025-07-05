package com.tahakamil.logaggregator.consumer.config.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class KafkaPropertiesLoader {
    private static final Logger logger = LoggerFactory.getLogger(KafkaPropertiesLoader.class);
    
    public static KafkaProperties load() {
        try (InputStream inputStream = KafkaPropertiesLoader.class.getResourceAsStream("/properties.yaml")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Properties file not found");
            }
            
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> kafkaConfig = (Map<String, Object>) data.get("kafka");
            
            KafkaProperties properties = new KafkaProperties();
            properties.setBootstrapServers((String) kafkaConfig.get("bootstrapServers"));
            properties.setTopic((String) kafkaConfig.get("topic"));
            properties.setGroupId((String) kafkaConfig.get("groupId"));
            
            logger.info("Loaded Kafka properties: {}", properties);
            return properties;
        } catch (Exception e) {
            logger.error("Failed to load Kafka properties", e);
            throw new RuntimeException("Failed to load Kafka properties", e);
        }
    }
}