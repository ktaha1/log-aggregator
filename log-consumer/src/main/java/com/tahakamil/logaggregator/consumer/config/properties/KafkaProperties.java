package com.tahakamil.logaggregator.consumer.config.properties;

import lombok.Data;

@Data
public class KafkaProperties {
    private String bootstrapServers;
    private String topic;
    private String groupId;
}