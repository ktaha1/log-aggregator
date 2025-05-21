package com.tahakamil.kafka.logaggregator.config.properties;

public class KafkaProperties {
    private String bootstrapServers;
    private String topic;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "KafkaProperties{" +
                "bootstrapServers='" + bootstrapServers + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
