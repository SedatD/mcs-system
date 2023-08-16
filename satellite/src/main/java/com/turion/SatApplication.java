package com.turion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, SqlInitializationAutoConfiguration.class})
public class SatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SatApplication.class, args);
        System.out.println("Satellite running...");
    }
}