package com.jacobs.basic.algorithm.dp;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by lichao on 2017/2/21.
 */
public class WordBreak {
  public static void main(String[] args) {

  }

  public ArrayList<String> wordBreak(String s, Set<String> dict) {
    HashMap<String, List<String>> map = new HashMap<>();
    return dfs(s, dict, map);
  }

  /**
   * 思路：将字符串S切割成为左右俩部分：S1和S2，如果S1包含在字典中，则只需要递归计算S2切割生成的字符串集合就好，最后将结果合并。
   */
  public ArrayList<String> dfs(String s, Set<String> dict, HashMap<String, List<String>> map) {
    if (map.containsKey(s)) {
      return (ArrayList<String>) map.get(s);
    }

    ArrayList<String> lists = new ArrayList<>();
    if (s.equals("")) {
      lists.add("");
    } else {
      int len = s.length();
      for (int i = 1; i < len; i++) {
        String sub = s.substring(0, i);
        if (dict.contains(sub)) {
          ArrayList t = dfs(s.substring(i, len), dict, map);
          if (t.size() == 0) {
            continue;
          } else {
            for (int j = 0; j < t.size(); j++) {
              StringBuilder sb = new StringBuilder();
              sb.append(sub)
                  .append(" ")
                  .append(t.get(j));
              lists.add(sb.toString()
                  .trim());
            }
          }
        }
      }
    }
    map.put(s, lists);
    return lists;
  }

  /**
   * 蛮力递归
   */
  public ArrayList<String> mdfs(String s, Set<String> dict) {
    if (s.equals("") || s == null) {
      return Lists.newArrayList("");
    }

    ArrayList<String> lists = new ArrayList<>();
    int length = s.length();
    for (int i = 0; i < length; i++) {
      String sub = s.substring(0, i);
      if (dict.contains(sub)) {
        ArrayList list = mdfs(sub.substring(i, length), dict);
        if (list.size() == 0) {
          continue;
        } else {
          for (int j = 0; j < list.size(); j++) {
            StringBuilder sb = new StringBuilder();
            sb.append(sub)
                .append(" ")
                .append(list.get(j));
            lists.add(sb.toString()
                .trim());
          }
        }
      }
    }
    return lists;
  }
}
