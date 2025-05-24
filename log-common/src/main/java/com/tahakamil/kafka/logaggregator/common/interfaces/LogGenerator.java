package com.tahakamil.kafka.logaggregator.common.interfaces;

import com.tahakamil.kafka.logaggregator.common.model.LogEvent;

public interface LogGenerator {
    LogEvent generateEvent();
}
