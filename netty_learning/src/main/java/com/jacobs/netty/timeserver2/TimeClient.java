package com.jacobs.netty.timeserver2;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by lichao on 2017/1/28. 使用LineBaseFrameDecoder 和 StringDecoder 支持tcp 粘包
 */
public class TimeClient {

  public void connnect(int port, String host) {
    //配置客户端NIO线程组
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group)
          .channel(NioSocketChannel.class)
          .option(ChannelOption.TCP_NODELAY, true)
          .handler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  socketChannel.pipeline()
                      .addLast(new LineBasedFrameDecoder(1024))
                      .addLast(new StringDecoder())
                      .addLast(new TimeClientHandler());
                }
              });

      //发起异步连接操作
      ChannelFuture f = b.connect(host, port)
          .sync();
      System.out.println(
          "The client has connected to the server ,host: " + host + " port: " + port);
      //等待客户端链路关闭
      f.channel()
          .closeFuture()
          .sync();

    } catch (Exception ex) {

    } finally {
      group.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    int port = 8080;
    new TimeClient().connnect(port, "127.0.0.1");
  }

  private class TimeClientHandler extends ChannelHandlerAdapter {

    private byte[] req;
    private int counter;

    public TimeClientHandler() {
      req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      ByteBuf message;
      for (int i = 0; i < 100; i++) {
        message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message);
      }
    }


    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      String body = (String) msg;
      System.out.println("NOW is: " + body + " ; the counter is : " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      ctx.close();
    }
  }
}
