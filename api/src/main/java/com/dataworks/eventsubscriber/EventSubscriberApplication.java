package com.dataworks.eventsubscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventSubscriberApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventSubscriberApplication.class, args);
    }

}
