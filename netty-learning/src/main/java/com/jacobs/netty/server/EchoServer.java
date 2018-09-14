package com.jacobs.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

/**
 * Created by lichao on 16/9/23.
 */
public class EchoServer {

  private final int port;

  public EchoServer(int port) {
    this.port = port;
  }

  public static void main(String[] args) throws Exception {
    new EchoServer(9999).start();
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

      //我们绑定的服务器，等待绑定完成。 （调用 sync() 的原因是当前线程阻塞）
      ChannelFuture f = b.bind()
          .sync();            //8
      System.out.println(
          EchoServer.class.getName() + " started and listen on " + f.channel()
              .localAddress());
      //（同步）等待服务器 Channel 关闭（因为我们 在 Channel 的 CloseFuture 上调用 sync()）
      f.channel()
          .closeFuture()
          .sync();            //9
    } finally {
      //我们可以关闭下 EventLoopGroup 并释放所有资源，包括所有创建的线程
      group.shutdownGracefully()
          .sync();            //10
    }
  }
}
