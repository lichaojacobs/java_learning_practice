package com.jacobs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
//@EnableEurekaClient
//@EnableJerseyConfiguration(scanPackage = "", applicationPath = "v1")
//@AutoGenerateRowMapper(scanPackage = "com.example.module", annotationFilter = RowMapper.class, outPath = "core/src/main/java/")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
