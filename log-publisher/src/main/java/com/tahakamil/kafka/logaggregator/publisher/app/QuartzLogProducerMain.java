package com.tahakamil.kafka.logaggregator.publisher.app;

import com.tahakamil.kafka.logaggregator.publisher.generator.FakeLogGenerator;
import com.tahakamil.kafka.logaggregator.publisher.job.LogPublishJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzLogProducerMain {
    public static void main(String[] args) throws SchedulerException {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("generator", new FakeLogGenerator());
        dataMap.put("count", 5);

        JobDetail job = JobBuilder.newJob(LogPublishJob.class)
                .withIdentity("logJob", "logGroup")
                .usingJobData(dataMap)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("logTrigger", "logGroup")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/10 * * * * ?")
                                .withMisfireHandlingInstructionDoNothing()
                )
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                scheduler.shutdown(true);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }));
    }
}
