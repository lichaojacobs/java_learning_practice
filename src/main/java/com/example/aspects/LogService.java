package com.example.aspects;

import org.springframework.stereotype.Component;

/**
 * Created by lichao on 16/8/1.
 */
@Component
public class LogService {
  @Log
  public void logTest(){
    System.out.println("this is the method");
  }
}
