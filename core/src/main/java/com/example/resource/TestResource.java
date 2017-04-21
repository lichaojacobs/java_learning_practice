package com.example.resource;

import com.example.jersey.ParamDesc;
import com.example.module.TestCase;
import com.example.service.ElasticSearchService;
import com.example.module.User;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lichao on 16/8/31.
 */
@Path("/hello")
public class TestResource {

  @Autowired
  ElasticSearchService elasticSearchService;


  @Path(("message"))
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

  @Path("elastic")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Object testElastic(
      @ParamDesc(isRequired = true, desc = "test key")
      @QueryParam("test_key")
          String testStr,
      @ParamDesc(isRequired = true, desc = "test value")
      @QueryParam("test_value")
          String value
  ) {
    return elasticSearchService.getTestReuslt();
  }

  @Path("test_case")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public TestCase getTestCase(
      @BeanParam TestCase testCase
  ) {

    return TestCase.builder().build();
  }
}
