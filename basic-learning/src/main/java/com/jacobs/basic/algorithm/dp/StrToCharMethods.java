package com.jacobs.basic.algorithm.dp;

/**
 * Created by lichao on 2017/10/4.
 */
// 数字字符串转换为字母组合的种数
// 给定一个字符串str, str全部由数字字符组成，如果str中某一个或某相邻俩个字符组成的子串
// 在1~26 之间，则这个子串可以转换为一个字母，规定"1"转换为A,"2"转换为B
public class StrToCharMethods {

  public static void main(String[] args) {

  }

  //暴力递归
  public static int num1(String str) {
    if (str == null || str.equals("")) {
      return 0;
    }

    char[] chars = str.toCharArray();
    return process(chars, 0);
  }

  public static int process(char[] chars, int i) {
    if (i == chars.length) {
      return 1;
    }

    if (chars[i] == '0') {
      return 0;
    }

    int res = process(chars, i + 1);
    if (i + 1 < chars.length && ((chars[i] - '0') * 10 + chars[i + 1] - '0') < 27) {
      res += process(chars, i + 2);
    }

    return res;
  }

  // 从后往前计算p(i)=p(i+1)+p(i+2)
  public static int num2(String str) {
    if (str == null || str.equals("")) {
      return 0;
    }

    char[] chars = str.toCharArray();
    int cur = chars[chars.length - 1] == '0' ? 0 : 1;
    int next = 1;
    int tmp = 0;
    for (int i = chars.length - 2; i >= 0; i--) {
      if (chars[i] == '0') {
        next = cur;
        cur = 0;
      } else {
        tmp = cur;
        if ((chars[i] - '0') * 10 + chars[i + 1] - '0' < 27) {
          cur += next;
          next = tmp;
        }
      }
    }

    return cur;
  }
}
