package com.example;

import com.example.jersey.EnableJerseyConfiguration;
import javafx.scene.Parent;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

//SpringBootApplication自动包括了ennableAutoConfiguration ComponentScan Configuration这些,
@SpringBootApplication
@EnableAsync
@EnableJerseyConfiguration(scanPackage = "", applicationPath = "v1")
public class Application {

  public static void main(String[] args) {
    //SpringApplication springApplication = SpringApplication.run(Application.class, args);
    new SpringApplicationBuilder()
        .sources(Parent.class)
        .child(Application.class)
        .bannerMode(Mode.LOG)
        .run(args);
  }
}
