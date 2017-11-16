package com.jacobs.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author lichao
 * @date 2017/11/9
 */
@EnableWebFlux
@SpringBootApplication
public class SpringWebFluxApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringWebFluxApplication.class, args);
  }
}
