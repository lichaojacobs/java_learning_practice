package com.jacobs.basic.algorithm.string;

import java.util.HashMap;

/**
 * Created by lichao on 2017/10/1.
 */
public class StringSamples {

  public static void main(String[] args) {
    //System.out.println(isDeformation("123", "3211"));
    //System.out.println(getNumSumInStr("A-1B--2C--D6E"));
    //System.out.println((char) 0);
    //System.out.println(getStrIndex(new String[]{null, "a", null, "a", "b", null, "c"}, "a"));
    //getAllStr(new char[]{'a', 'b', 'c'}, 0);
    System.out.println(getUnduplicatedSubStr("aabcb"));
  }

  //列出字符串所有顺序的可能情况:如 "abc" -> abc, acb, bac, bca, cab,cba,
  public static void getAllStr(char[] chars, int from) {
    if (chars == null || chars.length == 0) {
      return;
    }
    if (from == chars.length) {
      for (char s : chars) {
        System.out.print(s + " ");
      }
      System.out.println();
      return;
    }

    for (int i = from; i < chars.length; i++) {
      swap(chars, i, from);
      getAllStr(chars, from + 1);
      swap(chars, from, i);//将位置交换回来
    }
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

  //字符数组中，是否所有的字符都只出现过一次 (空间复杂度O(1))
  //要实现空间复杂度为O(1)的排序方法是不能使用快速排序的，
  // 因为需要使用函数栈空间，这样堆排序的额外空间复杂度就会增加至O(logN)
  public static boolean isUnique1(char[] chars) {
    if (chars == null || chars.length == 0) {
      return true;
    }

    heapSort(chars);
    for (int i = 1; i < chars.length; i++) {
      if (chars[i] == chars[i - 1]) {
        return false;
      }
    }
    return true;
  }

  public static void heapSort(char[] chars) {
    for (int i = 0; i < chars.length; i++) {
      heapInsert(chars, i);
    }
    for (int i = chars.length; i > 0; i--) {
      swap(chars, 0, i);
      heapify(chars, 0, i);
    }
  }

  public static void heapInsert(char[] chars, int i) {
    int parent = 0;
    while (i != 0) {
      parent = (i - 1) / 2;
      if (chars[parent] < chars[i]) {
        swap(chars, parent, i);
        i = parent;
      } else {
        break;
      }
    }
  }

  public static void heapify(char[] chars, int i, int size) {
    int left = i * 2 + 1;
    int right = i * 2 + 2;
    int largest = i;

    while (left < size) {
      if (chars[left] > chars[i]) {
        largest = left;
      }
      if (right < size && chars[right] > chars[largest]) {
        largest = right;
      }
      if (largest != i) {
        swap(chars, largest, i);
      } else {
        break;
      }

      i = largest;
      left = i * 2 + 1;
      right = i * 2 + 2;
    }
  }

  public static void swap(char[] chars, int index1, int index2) {
    char tmp = chars[index1];
    chars[index1] = chars[index2];
    chars[index2] = tmp;
  }

  public static int getStrIndex(String[] strs, String str) {
    if (strs == null || strs.length == 0 || str == null) {
      return -1;
    }

    int res = -1;
    int left = 0;
    int right = strs.length - 1;
    int mid = 0;
    int i = 0;

    while (left <= right) {
      mid = (left + right) / 2;
      if (strs[mid] != null && strs[mid].equals(str)) {
        res = mid;
        //这里不能break，为确保找到的位置始终是第一个出现的位置
        right = mid - 1;
      } else if (strs[mid] != null) {
        if (strs[mid].compareTo(str) < 0) {
          left = mid + 1;
        } else {
          right = mid - 1;
        }
      } else {
        //如果strs[mid]==null，该往左还是往右
        i = mid;
        while (strs[i] == null && --i >= left) {
          ;
        }

        if (i > left && strs[i].compareTo(str) < 0) {
          left = mid + 1;
        } else {
          //i到mid之间为null，没必要重复计算
          right = i - 1;
        }
      }
    }

    return res;
  }

  /**
   * 给一个字符串返回最长的不重复的字串长度
   *
   * @param str 字符串
   * @return 最长的不重复的字串长度
   */
  public static int getUnduplicatedSubStr(String str) {
    if (str == null || str.length() == 0) {
      return 0;
    }

    HashMap<Character, Integer> characterIntegerHashMap = new HashMap<>();
    char[] chars = str.toCharArray();
    int pre = 0;
    int aft = 0;
    int maxLength = 0;
    while (aft != chars.length) {
      if (!characterIntegerHashMap.containsKey(chars[aft])) {
        maxLength = Math.max(aft - pre + 1, maxLength);
      } else {
        pre = characterIntegerHashMap.get(chars[aft]);
        maxLength = Math.max(aft - pre + 1, maxLength);
      }

      characterIntegerHashMap.put(chars[aft], aft);
      aft++;
    }

    return maxLength;
  }
}
