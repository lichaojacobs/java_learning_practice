package com.jacobs.webflux.config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import reactor.netty.http.server.HttpServer;

public class EventLoopNettyCustomizer implements NettyServerCustomizer {

    @Override
    public HttpServer apply(HttpServer httpServer) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        return httpServer.tcpConfiguration(
                tcpServer -> tcpServer.bootstrap(serverBootstrap -> serverBootstrap
                        .group(parentGroup, childGroup).channel(NioServerSocketChannel.class)));
    }
}
