package com.jacobs.basic.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by lichao on 16/8/5.
 */
public class Test {

  public static void main(String[] args) {
    student student = new studentImpl("lichao");
//    CglibProxyFactory proxyFactory = new CglibProxyFactory();
//    student s2 = (student) proxyFactory.createStudent(student);
//    s2.print();
//    s2.yell();
    //    ProxyFactory proxyFactory = new ProxyFactory();
    //    student s2 = (student) proxyFactory.createStudentProxy(student);
    //    s2.print();
    //    s2.yell();
    Object proxyObject = Proxy.newProxyInstance(student.getClass().getClassLoader(),
        student.getClass().getInterfaces(), (proxy, method, params) -> {
          System.out.println("this is proxy print");
          return method.invoke(student, null);
        });

    ((student) proxyObject).yell();
  }
}
