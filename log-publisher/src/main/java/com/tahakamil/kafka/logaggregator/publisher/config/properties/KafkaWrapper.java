package com.tahakamil.kafka.logaggregator.publisher.config.properties;

public class KafkaWrapper {
    private KafkaProperties kafka;

    public KafkaProperties getKafka() {
        return kafka;
    }

    public void setKafka(KafkaProperties kafka) {
        this.kafka = kafka;
    }
}
