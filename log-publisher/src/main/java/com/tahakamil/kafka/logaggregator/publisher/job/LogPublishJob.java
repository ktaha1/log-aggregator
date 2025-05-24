package com.tahakamil.kafka.logaggregator.publisher.job;

import com.tahakamil.kafka.logaggregator.common.interfaces.LogGenerator;
import com.tahakamil.kafka.logaggregator.common.interfaces.LogPublisher;
import com.tahakamil.kafka.logaggregator.publisher.config.properties.KafkaProperties;
import com.tahakamil.kafka.logaggregator.publisher.config.properties.KafkaPropertiesLoader;
import com.tahakamil.kafka.logaggregator.publisher.impl.KafkaLogPublisher;
import com.tahakamil.kafka.logaggregator.publisher.service.LogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogPublishJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(LogPublishJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Load config
        KafkaProperties props = KafkaPropertiesLoader.loadDefault();
        // Instantiate publisher & service (auto-close via try-with-resources)
        try (LogPublisher publisher = new KafkaLogPublisher(props)) {
            LogService service = new LogService(publisher, (LogGenerator) context.getMergedJobDataMap().get("generator"));
            // Read count from JobDataMap or default:
            int count = context.getMergedJobDataMap().getInt("count");
            service.publishBatch(count);
        } catch (Exception e) {
            logger.error("Error executing LogPublishJob", e);
        }
    }
}