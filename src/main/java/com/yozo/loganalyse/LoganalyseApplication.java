package com.yozo.loganalyse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:logParttern.properties"})
@SpringBootApplication
public class LoganalyseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoganalyseApplication.class, args);
    }

}
