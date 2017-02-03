package com.example.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by lichao on 16/9/24.
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", //2
        CharsetUtil.UTF_8));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {                    //4
    cause.printStackTrace();
    ctx.close();
  }

  @Override
  protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)
      throws Exception {
    System.out.println("Client received: " + byteBuf.toString(CharsetUtil.UTF_8));    //3

  }
}
