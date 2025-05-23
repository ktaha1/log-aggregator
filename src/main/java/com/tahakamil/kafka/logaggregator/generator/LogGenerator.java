package com.tahakamil.kafka.logaggregator.generator;

import com.tahakamil.kafka.logaggregator.dto.LogEvent;

public interface LogGenerator {
    LogEvent generateEvent();
}
