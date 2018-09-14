package com.jacobs.netty.websocketx.server;

/**
 * Created by lichao on 2017/9/12.
 */


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * A HTTP server which serves Web Socket requests at:
 *
 * http://localhost:8080/websocket
 *
 * Open your browser at <a href="http://localhost:8080/">http://localhost:8080/</a>, then the demo
 * page will be loaded and a Web Socket connection will be made automatically.
 *
 * This server illustrates support for the different web socket specification versions and will work
 * with:
 *
 * <ul> <li>Safari 5+ (draft-ietf-hybi-thewebsocketprotocol-00) <li>Chrome 6-13
 * (draft-ietf-hybi-thewebsocketprotocol-00) <li>Chrome 14+ (draft-ietf-hybi-thewebsocketprotocol-10)
 * <li>Chrome 16+ (RFC 6455 aka draft-ietf-hybi-thewebsocketprotocol-17) <li>Firefox 7+
 * (draft-ietf-hybi-thewebsocketprotocol-10) <li>Firefox 11+ (RFC 6455 aka
 * draft-ietf-hybi-thewebsocketprotocol-17) </ul>
 */
public final class WebSocketServer {

  static final boolean SSL = System.getProperty("ssl") != null;
  static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "8080"));

  public static void main(String[] args) throws Exception {
    // Configure SSL.
    final SslContext sslCtx;
    if (SSL) {
      SelfSignedCertificate ssc = new SelfSignedCertificate();
      sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
    } else {
      sslCtx = null;
    }

    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new WebSocketServerInitializer(sslCtx));

      Channel ch = b.bind(PORT).sync().channel();

      System.out.println("Open your web browser and navigate to " +
          (SSL ? "https" : "http") + "://127.0.0.1:" + PORT + '/');

      ch.closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}
