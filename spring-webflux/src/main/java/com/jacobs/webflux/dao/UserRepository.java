package com.jacobs.webflux.dao;

import com.jacobs.webflux.model.NearByUserResponse;
import com.jacobs.webflux.model.User;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author lichao
 * @date 2017/11/11
 */
@Repository
public class UserRepository {

  private final List<User> users = Arrays
      .asList(new User(1, "jacobs", "lichaojacobs@tju.edu.cn"),
          new User(2, "chaoli", "chaoli@mobvoi.com"));
  private static String BASE_URI = "/nearbyUsers?wwid=296075535799459c9da864418a2cd02e&watch_device_id=b51b259ea257ef0ac6102931095d70b0&latitude=39.980704&longitude=116.298877&steps=1000";
  private static int RETRY_TIMES = 3;


  public Flux<User> listUsers() {
    return Flux.fromIterable(users);
  }

  public Mono<User> getUserById(int id) {
    return Mono.justOrEmpty(
        users.stream()
            .filter(user -> user.getId().equals(id))
            .findFirst().orElse(null))
        .retry(RETRY_TIMES);
  }

  public Flux<NearByUserResponse> listWatchUsers() {
    //exchange()返回Mono<ClientResponse>，它在Emits clientResponse可用时表示一个流。
    //WebClient.create("http://lbs-data-api").get().uri(baseUri).accept(MediaType.APPLICATION_JSON)
    //.exchange().flatMap(clientResponse -> clientResponse.bodyToFlux(NearByUserResponse.class))
    return WebClient
        .create("http://lbs-data-api")
        .get()
        .uri(BASE_URI)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(NearByUserResponse.class)
        //类似于熔断器的作用，出现异常可以暂时返回缓存的脏数据
        .onErrorReturn(ServerErrorException.class, new NearByUserResponse())
        .retry(RETRY_TIMES)
        .log();
  }

//  public Flux<User> listUsersFromDB() {
//    return Flux.fromIterable(commonJdbcTemplateFactory.getReadJdbcTemplate()
//        .query("select * from user", CommonRowMapper.getDefault(User.class)))
//        .onErrorReturn(IOException.class, users.get(0))//异常就塞一个默认的
//        .log();
//  }
}
