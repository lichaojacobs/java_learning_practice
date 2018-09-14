package com.jacobs.netty.websocketx.benchmarkserver;

/**
 * Created by lichao on 2017/9/12.
 */

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

/**
 * Handles handshakes and messages
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

  private static final String WEBSOCKET_PATH = "/websocket";

  private WebSocketServerHandshaker handshaker;

  @Override
  public void channelRead0(ChannelHandlerContext ctx, Object msg) {
    if (msg instanceof FullHttpRequest) {
      handleHttpRequest(ctx, (FullHttpRequest) msg);
    } else if (msg instanceof WebSocketFrame) {
      handleWebSocketFrame(ctx, (WebSocketFrame) msg);
    }
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

  private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
    // Handle a bad request.
    if (!req.decoderResult().isSuccess()) {
      sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
      return;
    }

    // Allow only GET methods.
    if (req.method() != GET) {
      sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
      return;
    }

    // Send the demo page and favicon.ico
    if ("/".equals(req.uri())) {
      ByteBuf content = WebSocketServerBenchmarkPage.getContent(getWebSocketLocation(req));
      FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

      res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
      HttpUtil.setContentLength(res, content.readableBytes());

      sendHttpResponse(ctx, req, res);
      return;
    }
    if ("/favicon.ico".equals(req.uri())) {
      FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
      sendHttpResponse(ctx, req, res);
      return;
    }

    // Handshake
    WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
        getWebSocketLocation(req), null, true, 5 * 1024 * 1024);
    handshaker = wsFactory.newHandshaker(req);
    if (handshaker == null) {
      WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
    } else {
      handshaker.handshake(ctx.channel(), req);
    }
  }

  private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

    // Check for closing frame
    if (frame instanceof CloseWebSocketFrame) {
      handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
      return;
    }
    if (frame instanceof PingWebSocketFrame) {
      ctx.write(new PongWebSocketFrame(frame.content().retain()));
      return;
    }
    if (frame instanceof TextWebSocketFrame) {
      // Echo the frame
      ctx.write(frame.retain());
      return;
    }
    if (frame instanceof BinaryWebSocketFrame) {
      // Echo the frame
      ctx.write(frame.retain());
    }
  }

  private static void sendHttpResponse(
      ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
    // Generate an error page if response getStatus code is not OK (200).
    if (res.status().code() != 200) {
      ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
      res.content().writeBytes(buf);
      buf.release();
      HttpUtil.setContentLength(res, res.content().readableBytes());
    }

    // Send the response and close the connection if necessary.
    ChannelFuture f = ctx.channel().writeAndFlush(res);
    if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
      f.addListener(ChannelFutureListener.CLOSE);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }

  private static String getWebSocketLocation(FullHttpRequest req) {
    String location = req.headers().get(HttpHeaderNames.HOST) + WEBSOCKET_PATH;
    if (WebSocketServer.SSL) {
      return "wss://" + location;
    } else {
      return "ws://" + location;
    }
  }
}
