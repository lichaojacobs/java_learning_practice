package com.jacobs.netty.redisclient;

/**
 * Created by lichao on 2017/9/13.
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Simple Redis client that demonstrates Redis commands against a Redis server.
 */
public class RedisClient {

  private static final String HOST = System.getProperty("host", "127.0.0.1");
  private static final int PORT = Integer.parseInt(System.getProperty("port", "6379"));

  public static void main(String[] args) throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group)
          .channel(NioSocketChannel.class)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline p = ch.pipeline();
              p.addLast(new RedisDecoder());
              p.addLast(new RedisBulkStringAggregator());
              p.addLast(new RedisArrayAggregator());
              p.addLast(new RedisEncoder());
              p.addLast(new RedisClientHandler());
            }
          });

      // Start the connection attempt.
      Channel ch = b.connect(HOST, PORT).sync().channel();

      // Read commands from the stdin.
      System.out.println("Enter Redis commands (quit to end)");
      ChannelFuture lastWriteFuture = null;
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      for (; ; ) {
        final String input = in.readLine();
        final String line = input != null ? input.trim() : null;
        if (line == null || "quit".equalsIgnoreCase(line)) { // EOF or "quit"
          ch.close().sync();
          break;
        } else if (line.isEmpty()) { // skip `enter` or `enter` with spaces.
          continue;
        }
        // Sends the received line to the server.
        lastWriteFuture = ch.writeAndFlush(line);
        lastWriteFuture.addListener(new GenericFutureListener<ChannelFuture>() {
          @Override
          public void operationComplete(ChannelFuture future) throws Exception {
            if (!future.isSuccess()) {
              System.err.print("write failed: ");
              future.cause().printStackTrace(System.err);
            }
          }
        });
      }

      // Wait until all messages are flushed before closing the channel.
      if (lastWriteFuture != null) {
        lastWriteFuture.sync();
      }
    } finally {
      group.shutdownGracefully();
    }
  }
}
