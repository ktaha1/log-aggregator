package com.tahakamil.kafka.logaggregator.generator;

import com.github.javafaker.Faker;
import com.tahakamil.kafka.logaggregator.dto.LogEvent;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;

public class FakeLogGenerator implements LogGenerator{
    private static final Faker faker = new Faker(new Locale("en"));
    private static final String[] levels = {"INFO","WARN","ERROR","DEBUG"};
    private static final String[] services = {"auth","order","payment","inventory"};
    private static final Random rnd = new Random();


    @Override
    public LogEvent generateEvent() {
        return LogEvent.builder()
                .timestamp(LocalDateTime.now())
                .service(generateService())
                .level(generateLevel())
                .message(generateMessage())
                .build();
    }

    private String generateLevel(){
        return levels[rnd.nextInt(levels.length)];
    }

    private String generateService() {
        return services[rnd.nextInt(services.length)];
    }

    private String generateMessage(){
        return faker.lorem().sentence();
    }
}
