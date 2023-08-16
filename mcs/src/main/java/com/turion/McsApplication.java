package com.turion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class McsApplication {

    public static void main(String[] args) {
        SpringApplication.run(McsApplication.class, args);
        System.out.println("MCS running...");
    }

}