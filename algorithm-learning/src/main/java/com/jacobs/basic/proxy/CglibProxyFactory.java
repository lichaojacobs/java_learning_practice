package com.jacobs.basic.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by lichao on 16/8/5.
 */
public class CglibProxyFactory implements MethodInterceptor {

  private Object object;

  public Object createStudent(Object object) {
    this.object = object;
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(object.getClass());
    enhancer.setCallback(this);
    return enhancer.create();
  }

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)
      throws Throwable {
    System.out.println("this is Cglib");
    return method.invoke(object, null);
  }
}
