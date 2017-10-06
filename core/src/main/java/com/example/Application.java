package com.example;

import javafx.scene.Parent;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
@EnableAsync
//@EnableJerseyConfiguration(scanPackage = "", applicationPath = "v1")
//@AutoGenerateRowMapper(scanPackage = "com.example.module", annotationFilter = RowMapper.class, outPath = "core/src/main/java/")
public class Application {

  public static void main(String[] args) {
    //SpringApplication springApplication = SpringApplication.run(Application.class, args);
    new SpringApplicationBuilder()
        .sources(Parent.class)
        .child(Application.class)
        .bannerMode(Mode.CONSOLE)
        .run(args);
  }
}
