package com.example.basic.algorithm.string;

/**
 * Created by lichao on 2017/10/1.
 */
public class StringSamples {

  public static void main(String[] args) {
    //System.out.println(isDeformation("123", "3211"));
    System.out.println(getNumSumInStr("A-1B--2C--D6E"));
    System.out.println((char) 0);
  }

  //判断两个字符串是否互为变形词
  public static boolean isDeformation(String str1, String str2) {
    if (str1 == null || str2 == null || (str1.length() != str2.length())) {
      return false;
    }

    int[] map = new int[256];
    for (char s1 : str1.toCharArray()) {
      map[s1]++;
    }

    for (char s2 : str2.toCharArray()) {
      map[s2]--;
      if (map[s2] < 0) {
        return false;
      }
    }

    return true;
  }

  //字符串中数字字串的求和（给定一个字符串str，求其中全部数字串所代表的数字之和）
  public static int getNumSumInStr(String str) {
    if (str == null || str.length() == 0) {
      return Integer.MIN_VALUE;//非法值
    }

    int result = 0;
    int minusTimes = 0;
    int numTimes = 0;
    //指向数字的开始与末尾
    int pre = 0;
    int aft = 0;
    int cur = 0;
    char[] chas1 = str.toCharArray();
    for (int i = 0; i < chas1.length; i++) {
      if (chas1[i] == '-') {
        minusTimes++;
      } else {
        cur = Integer.valueOf(chas1[i]) - '0';
        if (cur <= 9 && cur >= 0) {
          if (numTimes == 0) {
            pre = i;
          }
          aft = i;
          numTimes++;
        } else {
          //开始计算上一次遇到数字的结果
          if (numTimes != 0) {
            result = getTempResult(numTimes, pre, aft, chas1, minusTimes, result);
          }
          //清零
          minusTimes = 0;
          numTimes = 0;
        }
      }

      //如果末尾最后一个为数字时计算末尾
      if (i == chas1.length - 1) {
        result = getTempResult(numTimes, pre, aft, chas1, minusTimes, result);
      }
    }

    return result;
  }

  public static int getTempResult(int numTimes, int pre, int aft, char[] chas1, int minusTimes,
      int result) {
    int temp = 0;
    numTimes--;
    for (int k = pre; k <= aft; k++) {
      temp = temp + (chas1[k] - '0') * (int) (Math.pow(10, numTimes));
      numTimes--;
    }
    if (minusTimes % 2 == 0) {
      result += temp;
    } else {
      result += -temp;
    }

    return result;
  }

  //去掉字符串中连续出现k个0的子串
  //str="A00B", k=2 返回"A"
  public static String removeKZeroStr(String str, int k) {
    if (str == null || k < 1) {
      return str;
    }

    char[] chars = str.toCharArray();
    int count = 0;
    int start = -1;
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '0') {
        count++;
        start = start == -1 ? i : start;
      } else {
        if (count == k) {
          while (count-- != 0) {
            chars[start++] = 0;
          }
        }
        count = 0;
        start = -1;
      }
    }

    //循环完毕，对最末尾判断
    if (count == k) {
      while (count-- != 0) {
        chars[start++] = 0;
      }
    }

    return String.valueOf(chars);
  }

  //判断两个字符是否为旋转字符
  //如果一个字符串，把任意前面的部分挪到后面形成的字符串叫做str的旋转词
  //比如: str="12345" 旋转词有 "23451"...
  public static boolean isRotation(String str1, String str2) {
    if (str1 == null || str2 == null) {
      return false;
    }

    if (str1.length() != str2.length()) {
      return false;
    }

    //如果长度一样，先生成一个字符串b2，b2是两个字符串啊拼起来的结果，看是否包含字符串a
    //如 b2=1234512345
    String temp = str2 + str2;
    //这里可以用kmp算法
    return temp.contains(str1);
  }


}
