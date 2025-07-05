package com.tahakamil.logaggregator.consumer.app;

import com.tahakamil.logaggregator.consumer.config.properties.KafkaProperties;
import com.tahakamil.logaggregator.consumer.config.properties.KafkaPropertiesLoader;
import com.tahakamil.logaggregator.consumer.impl.KafkaLogConsumer;
import com.tahakamil.logaggregator.consumer.service.LogProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogConsumerMain {
    private static final Logger logger = LoggerFactory.getLogger(LogConsumerMain.class);
    
    public static void main(String[] args) {
        logger.info("Starting Kafka Log Consumer Application");
        
        try {
            // Load configuration
            KafkaProperties kafkaProperties = KafkaPropertiesLoader.load();
            
            // Create log processor
            LogProcessor logProcessor = new LogProcessor();
            
            // Create and start consumer
            KafkaLogConsumer consumer = new KafkaLogConsumer(kafkaProperties, logProcessor);
            
            // Add shutdown hook for graceful shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Shutting down log consumer...");
                consumer.stop();
                
                // Wait for consumer to stop
                int maxWaitSeconds = 30;
                int waitedSeconds = 0;
                while (consumer.isRunning() && waitedSeconds < maxWaitSeconds) {
                    try {
                        Thread.sleep(1000);
                        waitedSeconds++;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                
                if (consumer.isRunning()) {
                    logger.warn("Consumer did not stop gracefully within {} seconds", maxWaitSeconds);
                } else {
                    logger.info("Consumer stopped gracefully");
                }
            }));
            
            // Start consuming
            consumer.start();
            
        } catch (Exception e) {
            logger.error("Failed to start log consumer", e);
            System.exit(1);
        }
    }
}