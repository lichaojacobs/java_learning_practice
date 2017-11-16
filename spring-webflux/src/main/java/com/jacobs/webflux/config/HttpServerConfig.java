package com.jacobs.webflux.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;

/**
 * @author lichao
 * @date 2017/11/11
 */

//自定义 Http Server
@Slf4j
@Configuration
public class HttpServerConfig {

  @Autowired
  private Environment environment;

  @Bean
  public HttpServer httpServer(RouterFunction<?> routerFunction) {
    HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction);
    ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
    HttpServer server = HttpServer
        .create("localhost", Integer.valueOf(environment.getProperty("server.port")));
    server.newHandler(adapter);
    log.info("httpServer started...");
    return server;
  }
}
