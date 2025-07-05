package com.tahakamil.logaggregator.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogProcessor {
    private static final Logger logger = LoggerFactory.getLogger(LogProcessor.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public void processLog(String logMessage) {
        if (logMessage == null || logMessage.trim().isEmpty()) {
            logger.warn("Received empty or null log message");
            return;
        }
        
        try {
            // Parse and process the log message
            String processedMessage = parseLogMessage(logMessage);
            
            // Log the processed message
            logger.info("PROCESSED LOG: {}", processedMessage);
            
            // Here you could add additional processing logic such as:
            // - Storing to database
            // - Filtering based on log level
            // - Sending alerts for ERROR logs
            // - Aggregating metrics
            // - Writing to files
            
            // Example: Check for error logs and handle them specially
            if (isErrorLog(logMessage)) {
                handleErrorLog(processedMessage);
            }
            
        } catch (Exception e) {
            logger.error("Failed to process log message: {}", logMessage, e);
        }
    }
    
    private String parseLogMessage(String logMessage) {
        // Add processing timestamp
        String timestamp = LocalDateTime.now().format(formatter);
        return String.format("[%s] %s", timestamp, logMessage);
    }
    
    private boolean isErrorLog(String logMessage) {
        return logMessage.toLowerCase().contains("error") || 
               logMessage.toLowerCase().contains("exception") ||
               logMessage.toLowerCase().contains("fail");
    }
    
    private void handleErrorLog(String errorMessage) {
        // Special handling for error logs
        logger.warn("ERROR LOG DETECTED: {}", errorMessage);
        
        // Here you could implement:
        // - Send notifications
        // - Store in error log database
        // - Trigger alerts
        // - Update metrics
    }
}