package com.example.algorithm;

import java.util.Stack;

/**
 * Created by lichao on 16/10/5.
 */
public class ReverseStack {
  public static void main(String[] args) {

  }

  public int getAndRemoveLastElement(Stack<Integer> stack) {
    int result = stack.pop();
    if (stack.isEmpty()) {
      return result;
    } else {
      int last = getAndRemoveLastElement(stack);
      stack.push(result);
      return last;
    }
  }

  public void reverseStack(Stack<Integer> stack) {
    if (stack.isEmpty()) {
      return;
    }
    int last = getAndRemoveLastElement(stack);
    reverseStack(stack);
    stack.push(last);
  }
}
