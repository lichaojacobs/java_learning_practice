package com.jacobs.webflux.handler;

import com.jacobs.webflux.dao.UserRepository;
import com.jacobs.webflux.model.NearByUserResponse;
import com.jacobs.webflux.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author lichao
 * @date 2017/11/9
 */
@Component("userHandler")
public class UserHandler {

  @Autowired
  UserRepository userRepository;

  public Mono<ServerResponse> listUsers(ServerRequest request) {
    //extract the body into a Mono
    //Mono<String> strMono = request.bodyToMono(String.class);
    //extract the body int a flux
    //Flux<String> strFlux = request.bodyToFlux(String.class);

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(userRepository.listUsers(), User.class);
  }

  public Mono<ServerResponse> getUserById(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(userRepository.getUserById(Integer.valueOf(request.pathVariable("id"))), User.class);
  }

  public Mono<ServerResponse> getNearByWatchUsers(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(userRepository.listWatchUsers(), NearByUserResponse.class);
  }

//  public Mono<ServerResponse> listUsersFromDB(ServerRequest request) {
//    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//        .body(userRepository.listUsersFromDB(), User.class);
//  }

  public Mono<ServerResponse> deleteUser(ServerRequest request) {
    return null;
  }
}
