package com.jacobs.netty.timeserver1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lichao on 2017/1/28.
 */
public class TimeClientHandle implements Runnable {
  private String host;
  private int port;
  private Selector selector;
  private SocketChannel socketChannel;
  private volatile boolean stop;

  public TimeClientHandle(String host, int port) {
    try {
      this.host = host;
      this.port = port;
      selector = Selector.open();
      socketChannel = SocketChannel.open();
      socketChannel.configureBlocking(false);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void run() {
    try {
      doConnect();
    } catch (Exception ex) {
      ex.printStackTrace();
      System.exit(1);
    }
    while (!stop) {
      try {
        selector.select(1000);
        Set<SelectionKey> selectionKeySet = selector.selectedKeys();
        Iterator<SelectionKey> it = selectionKeySet.iterator();
        SelectionKey key = null;
        while (it.hasNext()) {
          key = it.next();
          it.remove();
          try {
            handleInput(key);
          } catch (Exception ex) {
            if (key != null) {
              key.cancel();
              if (key.channel() != null) {
                key.channel()
                    .close();
              }
            }
            ex.printStackTrace();
          }

        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

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
      SocketChannel sc = (SocketChannel) key.channel();
      if (key.isConnectable()) {
        if (sc.finishConnect()) {
          sc.register(selector, SelectionKey.OP_READ);
          doWrite(socketChannel);
        } else {
          // 连接失败退出
          System.exit(1);
        }
      }

      if (key.isReadable()) {
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int readBytes = sc.read(readBuffer);
        if (readBytes > 0) {
          readBuffer.flip();
          byte[] bytes = new byte[readBuffer.remaining()];
          readBuffer.get(bytes);
          String body = new String(bytes, "UTF-8");
          System.out.println("NOW is: " + body);
          this.stop = true;
        } else {
          key.cancel();
          sc.close();
        }
      }
    }
  }

  private void doConnect() throws IOException {
    //如果直接连接成功，则注册到多路复用器上，发送请求，读应答
    if (socketChannel.connect(new InetSocketAddress(host, port))) {
      socketChannel.register(selector, SelectionKey.OP_READ);
      doWrite(socketChannel);
    } else {
      socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }
  }

  private void doWrite(SocketChannel socketChannel) throws IOException {
    byte[] req = "QUERY TIME ORDER".getBytes();
    ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
    writeBuffer.put(req);
    writeBuffer.flip();
    socketChannel.write(writeBuffer);
    if (!writeBuffer.hasRemaining()) {
      System.out.println("Send order 2 server succeed.");
    }
  }


}
