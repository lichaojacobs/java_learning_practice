package com.jacobs.netty.aiotimeserver1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lichao on 2017/1/28.
 */
public class AsyncTimeServerHandler implements Runnable {

  private String host;
  private int port;
  CountDownLatch latch;
  AsynchronousServerSocketChannel asynchronousServerSocketChannel;

  public AsyncTimeServerHandler(String host, int port) {
    this.host = host;
    this.port = port;
    try {
      asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
      asynchronousServerSocketChannel.bind(new InetSocketAddress(this.host, this.port));
      System.out.println("The time server is start in port: " + port);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void run() {
    latch = new CountDownLatch(1);
    asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
    try {
      latch.await();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  public static class AcceptCompletionHandler
      implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
      attachment.asynchronousServerSocketChannel.accept(attachment, this);
      ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
      result.read(byteBuffer, byteBuffer, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
      exc.printStackTrace();
      attachment.latch.countDown();
    }
  }

  public static class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel readChannel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
      if (readChannel == null) {
        readChannel = channel;
      }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
      attachment.flip();
      byte[] body = new byte[attachment.remaining()];
      attachment.get(body);
      try {
        String req = new String(body, "UTF-8");
        System.out.println("the time server receive order: " + req);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new Date(
            System.currentTimeMillis()).toString() : "bad order";
        //处理请求
        doWrite(currentTime);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
      try {
        this.readChannel.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    private void doWrite(String currentTime) {
      if (currentTime != null && currentTime.trim()
          .length() > 0) {
        byte[] bytes = currentTime.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        readChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
          @Override
          public void completed(Integer result, ByteBuffer attachment) {
            //如果没有发送完成，继续发送
            if (attachment.hasRemaining()) {
              readChannel.write(attachment, attachment, this);
            }
          }

          @Override
          public void failed(Throwable exc, ByteBuffer attachment) {
            try {
              readChannel.close();
            } catch (IOException ex) {
              //ignore on close
            }
          }
        });
      }
    }

  }
}
