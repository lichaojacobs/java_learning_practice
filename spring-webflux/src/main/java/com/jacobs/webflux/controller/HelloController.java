package com.jacobs.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lichao
 * @date 2017/11/9
 */
@RestController
public class HelloController {

  @GetMapping("/hello")
  public String handle() {
    return "Hello WebFlux";
  }
}
