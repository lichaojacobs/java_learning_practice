package com.jacobs.netty.redisclient;

/**
 * Created by lichao on 2017/9/13.
 */

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.ErrorRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.IntegerRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.handler.codec.redis.SimpleStringRedisMessage;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * An example Redis client handler. This handler read input from STDIN and write output to STDOUT.
 */
public class RedisClientHandler extends ChannelDuplexHandler {

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
    String[] commands = ((String) msg).split("\\s+");
    List<RedisMessage> children = new ArrayList<>(commands.length);
    for (String cmdString : commands) {
      children.add(new FullBulkStringRedisMessage(ByteBufUtil.writeUtf8(ctx.alloc(), cmdString)));
    }
    RedisMessage request = new ArrayRedisMessage(children);
    ctx.write(request, promise);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    RedisMessage redisMessage = (RedisMessage) msg;
    printAggregatedRedisResponse(redisMessage);
    ReferenceCountUtil.release(redisMessage);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    System.err.print("exceptionCaught: ");
    cause.printStackTrace(System.err);
    ctx.close();
  }

  private static void printAggregatedRedisResponse(RedisMessage msg) {
    if (msg instanceof SimpleStringRedisMessage) {
      System.out.println(((SimpleStringRedisMessage) msg).content());
    } else if (msg instanceof ErrorRedisMessage) {
      System.out.println(((ErrorRedisMessage) msg).content());
    } else if (msg instanceof IntegerRedisMessage) {
      System.out.println(((IntegerRedisMessage) msg).value());
    } else if (msg instanceof FullBulkStringRedisMessage) {
      System.out.println(getString((FullBulkStringRedisMessage) msg));
    } else if (msg instanceof ArrayRedisMessage) {
      ((ArrayRedisMessage) msg).children()
          .forEach(RedisClientHandler::printAggregatedRedisResponse);
    } else {
      throw new CodecException("unknown message type: " + msg);
    }
  }

  private static String getString(FullBulkStringRedisMessage msg) {
    if (msg.isNull()) {
      return "(null)";
    }
    return msg.content().toString(CharsetUtil.UTF_8);
  }
}
