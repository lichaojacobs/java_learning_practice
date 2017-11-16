package com.jacobs.webflux.config;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.jacobs.webflux.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author lichao
 * @date 2017/11/9
 */
@Configuration
public class RoutingConfiguration {

  @Bean
  public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {

    return route(GET("/users").and(accept(APPLICATION_JSON)), userHandler::listUsers)
        .andRoute(GET("/user/{id}").and(accept(APPLICATION_JSON)),
            userHandler::getUserById)
        .andRoute(GET("/watchUsers").and(accept(APPLICATION_JSON)),
            userHandler::getNearByWatchUsers)
        .andRoute(DELETE("/{user}").and(accept(APPLICATION_JSON)), userHandler::deleteUser);
  }
}
