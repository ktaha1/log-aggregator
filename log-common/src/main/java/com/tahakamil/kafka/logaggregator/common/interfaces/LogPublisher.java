package com.tahakamil.kafka.logaggregator.common.interfaces;

import java.io.Closeable;

public interface LogPublisher extends Closeable {
    void publish(String key, String message);
    @Override
    void close();
}
