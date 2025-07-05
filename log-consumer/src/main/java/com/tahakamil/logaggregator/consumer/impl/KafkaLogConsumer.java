package com.tahakamil.logaggregator.consumer.impl;

import com.tahakamil.kafka.logaggregator.common.interfaces.LogConsumer;
import com.tahakamil.logaggregator.consumer.config.KafkaConsumerConfig;
import com.tahakamil.logaggregator.consumer.config.properties.KafkaProperties;
import com.tahakamil.logaggregator.consumer.service.LogProcessor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaLogConsumer implements LogConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaLogConsumer.class);
    
    private final KafkaConsumer<String, String> consumer;
    private final String topic;
    private final LogProcessor logProcessor;
    private final AtomicBoolean running = new AtomicBoolean(false);
    
    public KafkaLogConsumer(KafkaProperties kafkaProperties, LogProcessor logProcessor) {
        Properties props = KafkaConsumerConfig.build(kafkaProperties);
        this.consumer = new KafkaConsumer<>(props);
        this.topic = kafkaProperties.getTopic();
        this.logProcessor = logProcessor;
    }
    
    public void start() {
        if (running.compareAndSet(false, true)) {
            logger.info("Starting Kafka log consumer for topic: {}", topic);
            
            try {
                consumer.subscribe(Collections.singletonList(topic));
                
                while (running.get()) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                    
                    for (ConsumerRecord<String, String> record : records) {
                        try {
                            logger.debug("Received log message: partition={}, offset={}, key={}, value={}", 
                                    record.partition(), record.offset(), record.key(), record.value());
                            
                            logProcessor.processLog(record.value());
                        } catch (Exception e) {
                            logger.error("Error processing log message: {}", record.value(), e);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error in consumer loop", e);
            } finally {
                consumer.close();
                logger.info("Kafka log consumer stopped");
            }
        }
    }
    
    public void stop() {
        if (running.compareAndSet(true, false)) {
            logger.info("Stopping Kafka log consumer");
            consumer.wakeup();
        }
    }
    
    public boolean isRunning() {
        return running.get();
    }
}