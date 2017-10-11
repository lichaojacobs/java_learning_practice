package com.jacobs;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import javax.lang.model.element.Modifier;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by lichao on 2016/12/16.
 */
@SpringBootTest
public class JerseyTest {

  Client client = ClientBuilder.newClient();
  WebTarget target = client.target("http://localhost:9000");

  /**
   * Test to see that the message "Got it!" is sent in the response.
   */
  @Test
  public void testGetIt() {
    String responseMsg = target.path("v1/hello/message")
        .request()
        .get(String.class);
    System.out.println("Got it" + responseMsg);
  }

  public static void generateHelloworld() throws IOException {
    MethodSpec main = MethodSpec.methodBuilder("main") //main代表方法名
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)//Modifier 修饰的关键字
        .addParameter(String[].class, "args") //添加string[]类型的名为args的参数
        .addStatement("$T.out.println($S)", System.class,
            "Hello World")//添加代码，这里$T和$S后面会讲，这里其实就是添加了System,out.println("Hello World");
        .build();
    TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")//HelloWorld是类名
        .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
        .addMethod(main)  //在类中添加方法
        .build();
    JavaFile javaFile = JavaFile.builder("com.example.helloworld", typeSpec)
        .build();

    javaFile.writeTo(System.out);
  }
}
