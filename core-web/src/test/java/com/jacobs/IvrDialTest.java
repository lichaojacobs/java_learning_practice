package com.jacobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lichao on 2017/7/11.
 */
public class IvrDialTest {

  private static String AccountSid = "8aaf07085d106c7f015d25d4fe6005f1";
  private static String AccountToken = "f2a58affc3b344fb8b61475f63f50815";
  private static String AppId = "8aaf07085d106c7f015d2b6428f407cc";
  private static String ServerIP = "sandboxapp.cloopen.com";
  private static String ServerPort = "8883";
  private static String SoftVersion = "2013-12-26";
  private static String timestamp = "";

  public static void main(String[] args) {
    dail("18622042854", "1", "true");
  }

  /**
   * 向指定 URL 发送POST方法的请求
   *
   * @param url 发送请求的 URL
   * @param param 请求参数
   * @return 所代表远程资源的响应结果
   */
  public static String sendPost(String url, String param) {
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    try {

      //生成auth
      String src = AccountSid + ":" + timestamp;
      String auth = new sun.misc.BASE64Encoder().encode(src.getBytes());
      URL realUrl = new URL(url);
      // 打开和URL之间的连接
      URLConnection conn = realUrl.openConnection();

      // 设置通用的请求属性
      conn.setRequestProperty("accept", "application/xml");
      conn.setRequestProperty("connection", "close");
      conn.setRequestProperty("content-type", "application/xml;charset=utf-8");
      conn.setRequestProperty("authorization", auth);
      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(conn.getOutputStream());
      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(
          new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
      System.out.println("发送 POST 请求出现异常！" + e);
      e.printStackTrace();
    }
    //使用finally块来关闭输出流、输入流
    finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    System.out.println(result);
    return result;
  }

  public static String dail(String number, String userdata, String record) {
    //获取系统时间
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
    timestamp = df.format(new Date());
    //生成sig
    String signature = "";
    String sig = AccountSid + AccountToken + timestamp;
    try {
      signature = md5Digest(sig);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    //拼接请求包体
    String requsetbody = "";

    StringBuilder sb = new StringBuilder("<?xml version='1.0' encoding='utf-8'?><Request>");
    sb.append("<Appid>").append(AppId).append("</Appid>").append("<Dial number=").append("\"")
        .append(number).append("\"");
    if (record != null) {
      sb.append(" record=").append("\"").append(record).append("\"");
    }
    if (userdata != null) {
      sb.append(" userdata=").append("\"").append(userdata).append("\"");
    }
    sb.append("></Dial></Request>").toString();
    requsetbody = sb.toString();
    String url =
        "https://" + ServerIP + ":" + ServerPort + "/" + SoftVersion + "/Accounts/" + AccountSid
            + "/ivr/dial?sig=" + signature;
    return sendPost(url, requsetbody);

  }

  //MD5加密
  public static String md5Digest(String src)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] b = md.digest(src.getBytes("utf-8"));
    return byte2HexStr(b);
  }

  private static String byte2HexStr(byte[] b) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < b.length; ++i) {
      String s = Integer.toHexString(b[i] & 0xFF);
      if (s.length() == 1) {
        sb.append("0");
      }

      sb.append(s.toUpperCase());
    }
    return sb.toString();
  }
}
