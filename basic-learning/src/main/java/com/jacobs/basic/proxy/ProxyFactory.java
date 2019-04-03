package com.jacobs.basic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by lichao on 16/8/5.
 */
public class ProxyFactory implements InvocationHandler {

  private Object student;

  public Object createStudentProxy(Object student) {
    this.student = student;
    return Proxy
        .newProxyInstance(student.getClass().getClassLoader(), student.getClass().getInterfaces(),
            this);


  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("this is proxy");
    return method.invoke(student, null);
  }
}
