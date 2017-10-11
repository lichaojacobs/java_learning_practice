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
//    ConfigurableApplicationContext springApplication = new SpringApplicationBuilder()
//        .sources(Parent.class)
//        .child(Application.class)
//        .bannerMode(Mode.CONSOLE)
//        .run(args);

    SpringApplication springApplication = new SpringApplication(Application.class);
//    Map<String, Object> defaultMap = new HashMap<>();
//    defaultMap.put("spring.boot.admin.client.service-url", String.format("http://%s:50000",
//        InetAddress.getLocalHost().getCanonicalHostName()));
//    springApplication.setDefaultProperties(defaultMap);
    springApplication.run(args);
  }
}
