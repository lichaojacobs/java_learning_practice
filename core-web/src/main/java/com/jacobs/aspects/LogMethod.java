package com.jacobs.aspects;

import org.springframework.stereotype.Component;

/**
 * Created by lichao on 16/8/5.
 */
@Component
public class LogMethod {
  @LogBefore
  public void sayHello(){
    System.out.println("this is my Log");
  }
}
