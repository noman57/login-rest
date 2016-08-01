package com.ufril;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UfrilRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UfrilRestApiApplication.class, args);
    }
}
