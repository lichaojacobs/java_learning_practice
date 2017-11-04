package com.jacobs.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;

/**
 * Created by lichao on 16/9/24.
 */
public class EchoClient {

  private final String host;
  private final int port;

  public EchoClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void start() throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();                //1
      b.group(group)                                //2
          .channel(NioSocketChannel.class)            //3
          .remoteAddress(new InetSocketAddress(host, port))    //4
          .handler(new ChannelInitializer<SocketChannel>() {    //5
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline()
                  .addLast(new EchoClientHandler());
            }
          });

      //6 阻塞直到通道建立连接
      ChannelFuture f = b.connect()
          .sync();
      //7 阻塞直到channel关闭
      f.channel()
          .closeFuture()
          .sync();
    } finally {
      group.shutdownGracefully()
          .sync();            //8
    }
  }

  public static void main(String[] args) throws Exception {
//    if (args.length != 2) {
//      System.err.println("Usage: " + EchoClient.class.getSimpleName() +
//          " <host> <port>");
//      return;
//    }
//
//    final String host = args[0];
//    final int port = Integer.parseInt(args[1]);

    new EchoClient("127.0.0.1", 9999).start();
  }
}
