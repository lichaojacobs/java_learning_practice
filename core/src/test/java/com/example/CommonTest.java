package com.example;

import com.alibaba.fastjson.JSON;
import com.example.module.User;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lichao on 2016/11/24.
 */
public class CommonTest {

  public static void main(String[] args) throws InterruptedException {
//    ExecutorService executorService = Executors.newCachedThreadPool();

    //Thread.currentThread().join();
    System.out.println("hhhhh");
    User user = new User();
    user.setFirstName("chao");
    user.setLastName("li");
    user.setId(111L);

    System.out.println(JSON.toJSONString(user));
  }

  class CallTask implements Callable<Integer> {

    private int number;

    CallTask(int number) {
      this.number = number;
    }

    @Override
    public Integer call() throws Exception {
      return number;
    }
  }
}
