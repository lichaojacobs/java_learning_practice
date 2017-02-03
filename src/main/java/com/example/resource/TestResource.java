package com.example.resource;

import com.example.module.User;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lichao on 16/8/31.
 */
@Path("/hello")
public class TestResource {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> getMessage() {
    List<User> users = new ArrayList<>();
    User user1 = new User();
    user1.setFirstName("li");
    user1.setLastName("chao");
    user1.setId(1l);
    users.add(user1);
    User user2 = new User();
    user2.setFirstName("wang");
    user2.setLastName("lu");
    user2.setId(2l);
    users.add(user2);
    return users;
  }
}
