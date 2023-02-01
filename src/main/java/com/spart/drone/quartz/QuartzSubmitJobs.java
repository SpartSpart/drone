package com.spart.drone.quartz;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzSubmitJobs {
    @Value("${drone.quartz.cron}")
    private String DRONE_CRON;

    @Bean(name = "droneBatteryLevel")
    public JobDetailFactoryBean jobMemberStats() {
        return QuartzConfig.createJobDetail(DroneBatteryLevelJob.class, "Drone battery level");
    }

    @Bean(name = "droneBatteryLevelTrigger")
    public CronTriggerFactoryBean triggerMemberStats(@Qualifier("droneBatteryLevel") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, DRONE_CRON, "Drone battery level Statistics Trigger");
    }
}
