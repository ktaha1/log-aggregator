package com.tahakamil.kafka.logaggregator.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class LogEvent {
    private LocalDateTime timestamp;
    private String service;
    private String level;
    private String message;

    public String getBody() {
        return String.format(
                "%s [%s] (%s) %s",
                timestamp, level, service, message
        );
    }
}
