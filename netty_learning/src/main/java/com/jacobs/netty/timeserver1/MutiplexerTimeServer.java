package com.jacobs.netty.timeserver1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lichao on 2017/1/27.
 */
public class MutiplexerTimeServer implements Runnable {

  private Selector selector;
  private ServerSocketChannel serverSocketChannel;
  private volatile boolean stop;

  public MutiplexerTimeServer(String host, int port) {
    try {
      selector = Selector.open();
      serverSocketChannel = ServerSocketChannel.open();
      serverSocketChannel.configureBlocking(false);
      serverSocketChannel.socket()
          .bind(new InetSocketAddress(host, port));
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
      System.out.println("The time server is start in port: " + port);
    } catch (Exception ex) {
      ex.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void run() {
    while (!stop) {
      try {
        selector.select(1000);
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        SelectionKey key = null;
        while (iterator.hasNext()) {
          key = iterator.next();
          iterator.remove();
          try {
            handleInput(key);
          } catch (Exception ex) {
            key.cancel();
            key.channel()
                .close();
          }
        }

      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    //关闭多路复用器，所有注册在上面的channel和pipe自动释放
    if (selector != null) {
      try {
        selector.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private void handleInput(SelectionKey key) throws IOException {
    if (key.isValid()) {
      //处理新接入的请求信息
      if (key.isAcceptable()) {
        //accept the new connection
        ServerSocketChannel serverSocketChannel2 = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel2 = serverSocketChannel2.accept();
        socketChannel2.configureBlocking(false);
        //add new connection to the selector
        socketChannel2.register(selector, SelectionKey.OP_READ);
      }

      if (key.isReadable()) {
        //Read Data
        SocketChannel socketChannel3 = (SocketChannel) key.channel();
        //分配缓存空间
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int readBytes = socketChannel3.read(readBuffer);
        if (readBytes > 0) {
          readBuffer.flip();
          byte[] bytes = new byte[readBuffer.remaining()];
          readBuffer.get(bytes);
          String body = new String(bytes, "utf-8");
          System.out.println("the time server receive order :" + body);
          String currentTime = "Query time order".equalsIgnoreCase(body) ? new Date(
              System.currentTimeMillis()).toString() : "bad order";
          doWrite(socketChannel3, currentTime);
        } else {
          //对端链路关闭
          key.cancel();
          socketChannel3.close();
        }
      } else {
        //读到0字节，忽略
      }
    }
  }

  public void doWrite(SocketChannel channel, String response) throws IOException {
    if (response != null && response.trim()
        .length() > 0) {
      byte[] bytes = response.getBytes();
      ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
      writeBuffer.put(bytes);
      writeBuffer.flip();
      channel.write(writeBuffer);
    }
  }
}
