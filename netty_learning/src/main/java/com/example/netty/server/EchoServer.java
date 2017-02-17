package com.example.netty.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by lichao on 16/9/23.
 */
public class EchoServer {
  private final int port;

  public EchoServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) throws Exception {
    //    if (args.length != 1) {
    //      System.err.println("Usage: " + EchoServer.class.getSimpleName() +
    //          " <port>");
    //      return;
    //    }
    //    int port = Integer.parseInt(args[0]);        //1
    new EchoServer(9999).start();                //2
  }

  public void start() throws Exception {
    final ServerHandler serverHandler = new ServerHandler();
    NioEventLoopGroup group = new NioEventLoopGroup(); //3
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(group)                                //4
          .channel(NioServerSocketChannel.class)        //5
          .localAddress(new InetSocketAddress(port))    //6
          .childHandler(new ChannelInitializer<SocketChannel>() { //7pipline中加入eventHandler
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
              //可以一直用同一个实例
              ch.pipeline()
                  .addLast(serverHandler);
            }
          });

      ChannelFuture f = b.bind()
          .sync();            //8
      System.out.println(
          EchoServer.class.getName() + " started and listen on " + f.channel()
              .localAddress());
      f.channel()
          .closeFuture()
          .sync();            //9
    } finally {
      group.shutdownGracefully()
          .sync();            //10
    }
  }
}
