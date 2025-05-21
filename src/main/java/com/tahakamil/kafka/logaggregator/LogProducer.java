package com.tahakamil.kafka.logaggregator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class LogProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogProducer.class);

    public static void main(String[] args) {

        LOGGER.info("this is logging information ... {}", LocalDateTime.now());
        LOGGER.debug("this is debugging ... {}", LocalDateTime.now());

    }
}
