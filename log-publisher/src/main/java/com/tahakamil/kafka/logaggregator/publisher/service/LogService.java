package com.tahakamil.kafka.logaggregator.publisher.service;

import com.tahakamil.kafka.logaggregator.common.interfaces.LogGenerator;
import com.tahakamil.kafka.logaggregator.common.interfaces.LogPublisher;
import com.tahakamil.kafka.logaggregator.common.model.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);
    private final LogPublisher publisher;
    private final LogGenerator generator;

    public LogService(LogPublisher publisher, LogGenerator generator) {
        this.publisher = Objects.requireNonNull(publisher, "publisher must not be null");
        this.generator = Objects.requireNonNull(generator, "generator must not be null");
    }

    public void publishBatch(int count){
        for (int i = 0; i < count; i++) {
            LogEvent event = generator.generateEvent();
            publishEvent(i, event);
        }
    }


    private void publishEvent(int index, LogEvent event){
        try {
            publisher.publish(event.getService(), event.getBody());
            logger.debug("Published event #{}: {}", index, event);
        } catch (Exception ex) {
            logger.error("Failed to publish event #{} [service={}, body={}]",
                    index, event.getService(), event.getBody(), ex);
        }
    }
}
