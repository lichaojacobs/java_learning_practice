package com.jacobs.netty.subscribe;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by lichao on 2017/9/12.
 */
public class SubReqServer {

  public void bind(int port) throws Exception {
    //配置服务端NIO线程组

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 100)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override

            protected void initChannel(SocketChannel channel) throws Exception {
              /*channel.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                  .addLast(new ProtobufDecoder(SubscribeReq))*/
            }
          });

    } finally {
      //优雅退出，释放线程资源
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

}
