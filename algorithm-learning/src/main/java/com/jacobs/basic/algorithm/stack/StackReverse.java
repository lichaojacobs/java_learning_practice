package com.jacobs.basic.algorithm.stack;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

/**
 * Created by lichao on 2017/2/22.
 */
public class StackReverse {
  public static void main(String[] args) {
//    char[] chars = new char[]{'a', 'b', 'c'};
//    allSortsString(chars, 0, 3);
    sortBalls2();
  }

  //打印出一个字符串的字符全部排序
  public static void allSortsString(char[] chars, int start, int end) {
    if (start == end) {
      for (int i = 0; i < end; i++) {
        System.out.print(chars[i]);
      }
      System.out.println("");
    } else {
      for (int i = start; i < end; i++) {
        char temp = chars[start];
        chars[start] = chars[i];
        chars[i] = temp;

        allSortsString(chars, start + 1, end);//后序元素全排序

        temp = chars[start]; //对于当前层来说，循环的继续是需要复原初始的字符串顺序的
        chars[start] = chars[i];
        chars[i] = temp;
      }
    }
  }

  public static void sortBalls() {
    List<String> balls = Lists.newArrayList("R", "B", "G", "G", "R", "B", "B", "G", "R");
    int r = 0;
    int g = 1;
    int b = 2;

    String[] ballsArray = new String[balls.size()];
    for (int index = 0; index < balls.size(); index++) {
      String color = balls.get(index);
      if (color.equals("R")) {
        ballsArray[r] = balls.get(index);
        r = r + 3;
      } else if (color.equals("G")) {
        ballsArray[g] = balls.get(index);
        g = g + 3;
      } else if (color.equals("B")) {
        ballsArray[b] = balls.get(index);
        b = b + 3;
      }
    }

    Lists.newArrayList(ballsArray)
        .stream()
        .forEach(System.out::print);
  }

  public static void sortBalls2() {
    String[] balls = new String[]{"B", "B", "G", "G", "R", "B", "B", "G", "R", "G", "R", "R"};
    int r = 0;
    int g = 1;
    int b = 2;
    for (int i = 0; i < balls.length; i++) {
      int mod = i % 3;
      while (true) {
        String color = balls[i];
        if (mod == 0) {
          if (color.equals("R")) {
            if (r == i) {
              r = r + 3;
            }
            break;
            //continue;
          } else if (color.equals("G")) {
            swap(g, i, balls);
            g = g + 3;
          } else if (color.equals("B")) {
            swap(b, i, balls);
            b = b + 3;
          }
        } else if (mod == 1) {
          if (color.equals("G")) {
            if (i == g) {
              g = g + 3;
            }
            break;
            //continue;
          } else if (color.equals("R")) {
            swap(r, i, balls);
            r = r + 3;
          } else if (color.equals("B")) {
            swap(b, i, balls);
            b = b + 3;
          }
        } else if (mod == 2) {
          if (color.equals("B")) {
            if (i == b) {
              b = b + 3;
            }
            break;
            //continue;
          } else if (color.equals("G")) {
            swap(g, i, balls);
            g = g + 3;
          } else if (color.equals("R")) {
            swap(r, i, balls);
            r = r + 3;
          }
        }
      }
    }

    Lists.newArrayList(balls)
        .stream()
        .forEach(System.out::print);
  }

  public static void swap(int from, int to, String[] arrays) {
    String temp = arrays[from];
    arrays[from] = arrays[to];
    arrays[to] = temp;
  }

  public static int getAndRemoveLastElement(Stack<Integer> stack) {
    int result = stack.pop();
    if (stack.isEmpty()) {
      return result;
    } else {
      int last = getAndRemoveLastElement(stack);
      stack.push(result);

      return last;//栈已空，不压人最后一个节点，而是返回。
    }
  }

  public static void reverse(Stack<Integer> stack) {
    if (stack.isEmpty()) {
      return;
    }

    int last = getAndRemoveLastElement(stack);
    reverse(stack);
    stack.push(last);
  }
}
