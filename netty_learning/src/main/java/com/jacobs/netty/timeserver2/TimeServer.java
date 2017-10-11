package com.jacobs.netty.timeserver2;

import io.netty.bootstrap.ServerBootstrap;
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
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import java.util.Date;

/**
 * Created by lichao on 2017/1/28. 使用LineBaseFrameDecoder 和 StringDecoder 支持tcp 粘包
 */
public class TimeServer {

  public void bind(int port) throws Exception {
    EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossLoopGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024)
          .childHandler(new ChildChannelHandler());
      //绑定端口，同步等待成功
      ChannelFuture f = b.bind(port)
          .sync();
      System.out.println("The server start at port: " + port);
      //等待服务端监听窗口关闭
      f.channel()
          .closeFuture()
          .sync();
    } catch (Exception ex) {

    } finally {
      //优雅退出，释放线程池资源
      bossLoopGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
      socketChannel.pipeline()
          .addLast(new LineBasedFrameDecoder(1024))//解决tcp粘包的问题
          .addLast(new StringDecoder())
          .addLast(new TimeServerHandler());
    }
  }

  /**
   * 处理类
   */
  private class TimeServerHandler extends ChannelHandlerAdapter {

    private int counter;

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      String body = (String) msg;//加了 StringDecoder之后可以直接强制转换为String类型
      System.out.println(
          "The time server receive order: " + body + " ; the counter is: " + ++counter);
      String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(
          System.currentTimeMillis()).toString() : "BAD ORDER";
      currentTime = currentTime + System.getProperty("line.separator");
      ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
      ctx.writeAndFlush(resp);
    }


    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      //为了性能考虑, Netty 的write 方法不能直接将消息写入SocketChannel中
      // ，防止频繁的唤醒Selector进行消息发送。而是写入缓冲数组中，最后通过flush方法将缓冲区的消息全部写到SocketChannel中.
      ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      ctx.close();
    }
  }

  public static void main(String[] args) throws Exception {
    int port = 8080;
    new TimeServer().bind(port);
  }
}
