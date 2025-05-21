package com.tahakamil.kafka.logaggregator.config.properties;

import com.tahakamil.kafka.logaggregator.config.properties.KafkaProperties;

public class KafkaWrapper {
    private KafkaProperties kafka;

    public KafkaProperties getKafka() {
        return kafka;
    }

    public void setKafka(KafkaProperties kafka) {
        this.kafka = kafka;
    }
}
