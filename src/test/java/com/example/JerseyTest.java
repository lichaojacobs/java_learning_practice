package com.example;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

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
    String responseMsg = target.path("hello")
        .request()
        .get(String.class);
    System.out.println("Got it" + responseMsg);
  }
}
