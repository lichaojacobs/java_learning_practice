package com.jacobs.basic.algorithm.leetcode;

import com.alibaba.fastjson.JSON;
import com.jacobs.basic.ListNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by lichao on 2017/10/6.
 */
public class Problems {

  public static void main(String[] args) {
//    String[] tokens = new String[]{"0", "3", "/"};
//    System.out.println(evalRPN_01(tokens));

//    ListNode root = new ListNode(1);
//    root.next = new ListNode(2);
//    System.out.println(hasCycle_02(root));

    System.out.println(JSON.toJSONString(
        wordBreak_04("catsanddog", Sets.newHashSet("cats", "cat", "sand", "and", "dog"))));

    System.out.println(wordBreak_03_2("a", Sets.newHashSet("a")));
  }


  //  Evaluate the value of an arithmetic expression in Reverse Polish Notation.
//
//  Valid operators are +, -, *, /. Each operand may be an integer or another expression.
//
//  Some examples:
//
//      ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
//      ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
  public static int evalRPN_01(String[] tokens) {
    if (tokens == null || tokens.length == 0) {
      return -1;//非法值
    }

    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < tokens.length; i++) {
      if (!tokens[i].equals("+")
          && !tokens[i].equals("-")
          && !tokens[i].equals("*")
          && !tokens[i].equals("/")) {
        stack.push(Integer.valueOf(tokens[i]));
      } else {
        int n = stack.pop();
        int m = stack.pop();

        if (tokens[i].equals("+")) {
          stack.push(m + n);
        }

        if (tokens[i].equals("-")) {
          stack.push(m - n);
        }

        if (tokens[i].equals("*")) {
          stack.push(m * n);
        }

        if (tokens[i].equals("/")) {
          stack.push(m / n);
        }

      }
    }

    return stack.pop();
  }

  //  Given a linked list, determine if it has a cycle in it.
//  Follow up:
//  Can you solve it without using extra space?
  public static boolean hasCycle_02(ListNode head) {
    if (head == null) {
      return false;
    }

    ListNode pre = head;
    ListNode aft = head;

    while (aft != null && aft.next != null) {
      pre = pre.next;
      aft = aft.next.next;

      if (pre == aft) {
        return true;
      }
    }

    return false;
  }

  //  Given a string s and a dictionary of words dict, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
//  For example, given s = "leetcode", dict = ["leet", "code"].
//  Return true because "leetcode" can be segmented as "leet code".
  public boolean wordBreak_03_1(String s, Set<String> wordDict) {
    boolean[] dp = new boolean[s.length() + 1];
    Arrays.fill(dp, false);
    dp[s.length()] = true;
    // 外层循环递增长度
    for (int i = s.length() - 1; i >= 0; i--) {
      // 内层循环寻找分割点
      for (int j = i; j < s.length(); j++) {
        String sub = s.substring(i, j + 1);
        if (wordDict.contains(sub) && dp[j + 1]) {
          dp[i] = true;
          break;
        }
      }
    }
    return dp[0];
  }

  public static boolean wordBreak_03_2(String s, Set<String> dict) {
    if (s == null || dict == null || dict.isEmpty()) {
      return false;
    }

    boolean[] dp = new boolean[s.length() + 1];
    Arrays.fill(dp, false);
    dp[0] = true;

    for (int i = 0; i < s.length(); i++) {
      if (dp[i] == false) {
        continue;
      }

      for (String str : dict) {
        int length = str.length();
        if (i + length > s.length()) {
          break;
        }
        String expect = s.substring(i, i + length);
        if (str.equals(expect) && dp[i]) {
          dp[i + length] = true;
        }
      }
    }

    return dp[s.length()];
  }

  //  Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a valid dictionary word.
//  Return all such possible sentences.
//      For example, given
//  s ="catsanddog",
//  dict =["cat", "cats", "and", "sand", "dog"].
//  A solution is["cats and dog", "cat sand dog"].

  //  我们从第一个字母开始，遍历字典，看从第一个字母开始能组成哪个在字典里的词，
//  如果找到一个，就在这个词的结束位置下一个字母处，建立一个列表，记录下这个词（保存到一个列表的数组）。
//  当遍历完这个词典并找出所有以第一个字母开头的词以后，我们进入下一轮搜索。下一轮搜索只在之前找到的词的后面位置开始，如果这个位置不是一个词的下一个字母位置，我们就跳过。
//  这样我们相当于构建了一个树（实际上是列表数组），每个找到的词都是这个树的一个分支。有了这个“树”，然后我们再用深度优先搜索，把路径加到结果当中就行了。

  //  c
//      a
//  t                *              *
//  s -- cat         |              |
//  a -- cats        cats          cats
//  n                 \            /
//  d                  \          /
//  d -- and, sand      and    sand
//  o                    \      /
//  g                     \    /
//      -- dog              dog
  public static ArrayList<String> wordBreak_04(String s, Set<String> dict) {
    if (s == null || dict == null || dict.isEmpty()) {
      return new ArrayList<>();
    }

    ArrayList<String> resultList = new ArrayList<>();
    List<String> dp[] = new ArrayList[s.length() + 1];
    dp[0] = new ArrayList<>();

    for (int i = 0; i < s.length(); i++) {
      // 只在单词的后一个字母开始寻找，否则跳过
      if (dp[i] == null) {
        continue;
      }
      // 看从当前字母开始能组成哪个在字典里的词
      for (String word : dict) {
        int len = word.length();
        if (i + len > s.length()) {
          continue;
        }
        String sub = s.substring(i, i + len);
        if (word.equals(sub)) {
          if (dp[i + len] == null) {
            dp[i + len] = new ArrayList<>();
          }
          dp[i + len].add(0, word);
        }
      }
    }

    // 如果数组末尾不存在单词，说明找不到分解方法
    if (dp[s.length()] != null) {
      backTrack(dp, s.length(), new ArrayList<>(), resultList);
    }
    return resultList;
  }

  //深度遍历将路径添加即可
  private static void backTrack(List<String> dp[], int end, ArrayList<String> tmp,
      List<String> res) {
    if (end <= 0) {
      String path = tmp.get(tmp.size() - 1);
      for (int i = tmp.size() - 2; i >= 0; i--) {
        path += " " + tmp.get(i);
      }
      res.add(path);
      return;
    }
    //dp[i]可能为多个,从第一个走到底再退栈，继续遍历到底就是所谓的深度遍历
    for (String word : dp[end]) {
      tmp.add(word);
      backTrack(dp, end - word.length(), tmp, res);
      tmp.remove(tmp.size() - 1);
    }
  }

  public static ArrayList<String> wordBreak_2(String s, Set<String> dict) {
    if (s == null || dict == null || dict.isEmpty()) {
      return new ArrayList<>();
    }

    ArrayList<String> results = Lists.newArrayList();
    //当前字母开头的单词的最后一词的下一个位置存一个list
    List<String> dp[] = new ArrayList[s.length() + 1];
    dp[0] = new ArrayList<>();

    for (int i = 0; i < s.length(); i++) {
      //只从单词后一个开始,否则跳过
      if (dp[i] == null) {
        continue;
      }

      for (String word : dict) {
        int length = word.length();
        if (i + length > s.length()) {
          continue;
        }

        String expect = s.substring(i, i + length);
        if (word.equals(expect)) {
          if (dp[i + length] == null) {
            dp[i + length] = Lists.newArrayList(expect);
          } else {
            dp[i + length].add(expect);
          }
        }
      }
    }

    if (dp[s.length()] != null) {
      backTrack(dp, s.length(), new ArrayList<>(), results);
    }

    return results;
  }

  public static void backTrack(List<String> dp[], int length, ArrayList<String> tmp,
      ArrayList<String> results) {
    if (length <= 0) {
      String path = tmp.get(tmp.size() - 1);
      for (int i = tmp.size() - 2; i >= 0; i--) {
        path = path + " " + tmp.get(i);
      }
      results.add(path);
      return;
    }

    for (String str : dp[length]) {
      tmp.add(str);
      backTrack(dp, length - str.length(), tmp, results);
      tmp.remove(tmp.size() - 1);
    }
  }
}
