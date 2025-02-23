package com.Musicom.updater;

import com.Musicom.updater.scheduler.UpdaterJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.Musicom.data", "com.Musicom.spotify_client"})
public class UpdaterConfig {
    @Bean
    public JobDetail updaterJobDetail() {
        return JobBuilder
                .newJob(UpdaterJob.class)
                .withIdentity("updater")
                .withDescription("3600")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger updaterTrigger(JobDetail updaterJobDetail) {
        return TriggerBuilder
                .newTrigger()
                .forJob(updaterJobDetail)
                .withIdentity("updater")
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder
                        .simpleSchedule()
                        .repeatForever()
                        .withIntervalInHours(1)
                ).build();
    }
}
