package com.yozo.loganalyse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

@MapperScan(basePackages = "com.yozo.loganalyse.mapper")
@PropertySource(value = {"classpath:logParttern.properties","classpath:Common.properties"})
@SpringBootApplication
public class LoganalyseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoganalyseApplication.class, args);
    }

}
