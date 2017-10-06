package com.example.basic;

import lombok.Data;

/**
 * Created by lichao on 2017/10/6.
 */
@Data
public class ListNode {

  public int val;
  public ListNode next;

  public ListNode(int x) {
    val = x;
    next = null;
  }
}
