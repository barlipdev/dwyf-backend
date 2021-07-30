package com.barlipdev.dwyf.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTasks {

    //to do checking user products state (its safe to eat)
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("Works");
    }
}
