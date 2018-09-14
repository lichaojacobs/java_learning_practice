package com.jacobs.netty.websocketx.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Echoes uppercase content of text frames.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketFrameHandler.class);
  /**
   * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group. A
   * closed Channel is automatically removed from the collection,
   */
  public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
    Channel incoming = ctx.channel();
    for (Channel channel : channels) {
      // ping and pong frames already handled
      if (frame instanceof TextWebSocketFrame) {
        String request = ((TextWebSocketFrame) frame).text();

        if (channel != incoming) {
          channel.writeAndFlush(
              new TextWebSocketFrame(
                  simpleDateFormat.format(new java.util.Date()) + ": [" + incoming.remoteAddress()
                      + "]: " + request.toLowerCase(Locale.US)));
        } else {
          channel.writeAndFlush(new TextWebSocketFrame(
              simpleDateFormat.format(new java.util.Date()) + ": [me]: " + request
                  .toLowerCase(Locale.US)));
        }
      } else {
        String message = "unsupported frame type: " + frame.getClass().getName();
        throw new UnsupportedOperationException(message);
      }
    }
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Channel incoming = ctx.channel();
    for (Channel channel : channels) {
      channel
          .writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));
    }
    channels.add(ctx.channel());
    System.out.println("Client:" + incoming.remoteAddress() + "加入");
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    Channel incoming = ctx.channel();
    for (Channel channel : channels) {
      channel
          .writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
    }
    channels.remove(ctx.channel());
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    Channel incoming = ctx.channel();
    System.out.println("Client:" + incoming.remoteAddress() + "在线");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
    Channel incoming = ctx.channel();
    System.out.println("Client:" + incoming.remoteAddress() + "掉线");
  }
}
