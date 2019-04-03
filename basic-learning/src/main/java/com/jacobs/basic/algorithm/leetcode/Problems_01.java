package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.algorithm.TreeNode;
import com.jacobs.basic.models.ListNode;
import com.jacobs.basic.models.RandomListNode;
import com.jacobs.basic.models.UndirectedGraphNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by lichao on 2017/10/6.
 */
public class Problems_01 {

  public static void main(String[] args) {
//    String[] tokens = new String[]{"0", "3", "/"};
//    System.out.println(evalRPN_01(tokens));

//    ListNode root = new ListNode(1);
//    root.next = new ListNode(2);
//    System.out.println(hasCycle_02(root));

//    System.out.println(JSON.toJSONString(
//        wordBreak_04("catsanddog", Sets.newHashSet("cats", "cat", "sand", "and", "dog"))));
//
//    System.out.println(wordBreak_03_2("a", Sets.newHashSet("a")));
//    RandomListNode testNode = new RandomListNode(1);
//    RandomListNode resultNode = copyRandomList_05(testNode);
//    System.out.println(resultNode.label);

//    System.out.println(candy_11(
//        new int[]{58, 21, 72, 77, 48, 9, 38, 71, 68, 77, 82, 47, 25, 94, 89, 54, 26, 54, 54, 99, 64,
//            71, 76, 63, 81, 82, 60, 64, 29, 51, 87, 87, 72, 12, 16, 20, 21, 54, 43, 41, 83, 77, 41,
//            61, 72, 82, 15, 50, 36, 69, 49, 53, 92, 77, 16, 73, 12, 28, 37, 41, 79, 25, 80, 3, 37,
//            48, 23, 10, 55, 19, 51, 38, 96, 92, 99, 68, 75, 14, 18, 63, 35, 19, 68, 28, 49, 36, 53,
//            61, 64, 91, 2, 43, 68, 34, 46, 57, 82, 22, 67, 89}));

    TreeNode root = new TreeNode(4);
    root.left = new TreeNode(9);
    root.left.left = new TreeNode(0);
    root.right = new TreeNode(1);
    System.out.println(sumNumbers_016(root));
  }


  //  Evaluate the val of an arithmetic expression in Reverse Polish Notation.
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

  //  A linked list is given such that each node contains an additional random pointer
// which could point to any node in the list or null.
//  Return a deep copy of the list.
  public static RandomListNode copyRandomList_05(RandomListNode head) {
    if (head == null) {
      return head;
    }

    RandomListNode pre = head;
    RandomListNode aft;

    //first: copy next
    while (pre != null) {
      aft = pre.next;
      RandomListNode tmpNode = new RandomListNode(pre.label);
      tmpNode.next = aft;
      pre.next = tmpNode;
      pre = aft;
    }

    //second copy random
    pre = head;
    aft = pre.next;
    while (aft != null) {
      if (pre.random == null) {
        aft.random = null;
      } else {
        aft.random = pre.random.next;
      }
      pre = pre.next.next;
      if (aft.next == null) {
        aft = null;
      } else {
        aft = aft.next.next;
      }
    }

    //third split node
    pre = head;
    aft = pre.next;
    RandomListNode newHead = aft;
    while (aft != null) {
      pre.next = aft.next;
      if (pre.next == null) {
        aft.next = null;
      } else {
        aft.next = pre.next.next;
      }
      pre = pre.next;
      aft = aft.next;
    }

    return newHead;
  }


  //Given an array of integers, every element appears three times except for one. Find that single one.
//  Note:
//  Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
  public static int singleNumber_06(int[] A) {
    return 0;
  }

  //找出都出现俩次的数组中，唯一出现一次的数组
  public static int singleNumber_07(int[] arr) {
    if (arr == null || arr.length == 0 || arr.length < 3) {
      return -1;//非法值
    }

    int result = arr[0];
    for (int i = 1; i < arr.length; i++) {
      result = result ^ arr[i];
    }

    return result;
  }

  //所有其他数字出现N（N>=2）次，而一个数字出现1次。思路：每位上1的个数肯定也能被3整除
  public static int singleNumber_08(int a[], int n) {
    int bits[] = new int[32];
    Arrays.fill(bits, 0);
    int i, j;
    for (i = 0; i < n; i++) {
      for (j = 0; j < 32; j++) {
        bits[j] += ((a[i] >> j) & 1);
      }
    }
    // 如果某位上的结果不能被整除，则肯定目标数字在这一位上为
    int result = 0;
    for (j = 0; j < 32; j++) {
      if (bits[j] % 3 != 0) {
        result += (1 << j);
      }
    }
    return result;
  }

  //一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
  //思路：将数组分成两部分，每一部分只包含一个出现一次的数字，这样就就能找出两个数字
  //1. 除了有两个数字只出现了一次，其他数字都出现了两次。异或运算中，任何一个数字和自己本身异或都是0，任何一个数字和0异或都是本身。
//2. 如果尝试把原数组分成两个子数组，且刚好每个子数组中各自包含一个只出现一次的数字。则在该前提下，每个子数组中，只有一个数字出现了一次，其他数字都出现了两次。
//3. 针对每个子数组，从头到尾依次异或每个数字，则最后留下来的就是只出现了一次的数字。因为出现两次的都抵消掉了。
//4. 怎样实现子数组的划分呢。若对原数组从头到尾的进行异或，则最后得到的结果就是两个只出现一次的数字的异或运算结果。这个结果的二进制表示中，至少有一位为1，因为这两个数不相同。该位记为从最低位开始计数的第n位。
//5. 则分组的标准定为从最低位开始计数的第n位是否为1。因为出现两次的同一个数字，各个位数上都是相同的，所以一定被分到同一个子数组中，且每个子数组中只包含一个出现一次的数字。
  public static void singleNumber_09(int array[], int[] num1, int[] num2) {
    int length = array.length;
    if (length == 2) {
      num1[0] = array[0];
      num2[0] = array[1];
    }

    int bitResult = 0;
    for (int i = 0; i < length; ++i) {
      bitResult ^= array[i];
    }

    //从低位到高位，找到一个为1的位置。因为出现两次的同一个数字，各个位数上都是相同的，所以一定被分到同一个子数组中，且每个子数组中只包含一个出现一次的数字。
    int index = 0;
    while (((bitResult & 1) == 0) && index < 32) {
      bitResult >>= 1;
      index++;
    }

    for (int i = 0; i < length; ++i) {
      if (((array[i] << index) & 1) == 1) {
        num1[0] ^= array[i];
      } else {
        num2[0] ^= array[i];
      }
    }
  }

  // DP Program
  //  There are N children standing in a line. Each child is assigned a rating val.
//  You are giving candies to these children subjected to the following requirements:
//  Each child must have at least one candy.
//  Children with a higher rating get more candies than their neighbors.
//  What is the minimum candies you must give?
  public static int candy_10(int[] ratings) {
//    if (ratings == null || ratings.length == 0) {
//      return -1;//非法值
//    }

    //用来存储每个rating拿到的糖果，dp[i]表示，0到i以前的都已经排好序了
    int[] dp = new int[ratings.length];
    //令初始值为1
    dp[0] = 1;
    int totalSum = 0;

    for (int i = 1; i < ratings.length; i++) {
      if (ratings[i] > ratings[i - 1]) {
        dp[i] = dp[i - 1] + 1;
      } else if (dp[i - 1] > 1) {
        dp[i] = 1;
      } else {
        //首先dp[i]
        dp[i] = 1;
        //重置前面的值
        for (int j = i; j > 0; j--) {
          if ((ratings[j - 1] > ratings[j]) && (dp[j - 1] <= dp[j])) {
            dp[j - 1] = dp[j] + 1;
          } else {
            break;
          }
        }
      }
    }

    for (int i = 0; i < ratings.length; i++) {
      totalSum += dp[i];
    }

    return totalSum;
  }

  //最佳解法:从左到右遍历一遍，再从右到左遍历一遍，这样就能确保两个方向上的数都是正确的
  public static int candy_11(int[] ratings) {

    if (ratings == null || ratings.length <= 1) {
      return 1;
    }

    int[] candy = new int[ratings.length];
    int sum = 0;
    for (int i = 0; i < candy.length; i++) {
      candy[i] = 1;
    }
    for (int i = 1; i < candy.length; i++) {
      if (ratings[i] > ratings[i - 1]) {
        candy[i] = candy[i - 1] + 1;
      }
    }
    for (int i = candy.length - 1; i > 0; i--) {
      if (ratings[i] < ratings[i - 1] && candy[i] >= candy[i - 1]) {
        candy[i - 1] = candy[i] + 1;
      }
    }

    for (int i = 0; i < candy.length; i++) {
      sum += candy[i];
    }
    return sum;
  }

  //  There are N gas stations along a circular route, where the amount of gas at station i isgas[i].
//  You have a car with an unlimited gas tank and it costscost[i]of gas to travel from station i to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.
//  Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.
//  Note:
//  The solution is guaranteed to be unique.
  //
  public static int canCompleteCircuit_012(int[] gas, int[] cost) {
    if (gas == null || cost == null || gas.length == 0 || cost.length == 0
        || gas.length != cost.length) {
      return -1;
    }

    //计算差值数组
    int[] minusArr = new int[gas.length];
    int sum = 0;
    for (int i = 0; i < gas.length; i++) {
      minusArr[i] = gas[i] - cost[i];
      sum += minusArr[i];
    }

    //差值数组之和如果小于0，则肯定找不到解
    if (sum < 0) {
      return -1;
    }

    //利用贪心求解
    int index = -1;
    sum = 0;
    for (int i = 0; i < minusArr.length; i++) {
      if (index == -1) {
        if (minusArr[i] >= 0) {
          sum += minusArr[i];
          index = i;
        }
      } else {
        if (sum + minusArr[i] >= 0) {
          sum += minusArr[i];
        } else {
          index = -1;
        }
      }
    }

    return index;
  }


  //Clone an undirected graph. Each node in the graph contains alabeland a list of itsneighbors.
  //考察图的遍历算法， DFS / BFS。
  //基本思想， 利用一个map数据结构， 在遍历图的过程中， 创建相对应的新的图的部分
  public static UndirectedGraphNode cloneGraph_013(UndirectedGraphNode node) {
    if (node == null) {
      return null;
    }

    //存储旧节点到新节点的映射
    Map<UndirectedGraphNode, UndirectedGraphNode> relationMap = new HashMap<>();
    UndirectedGraphNode head = new UndirectedGraphNode(node.label);
    relationMap.put(node, head);

    Stack<UndirectedGraphNode> stack = new Stack<UndirectedGraphNode>();
    stack.push(node);
    while (!stack.isEmpty()) {
      UndirectedGraphNode temp = stack.pop();
      ArrayList<UndirectedGraphNode> lists = new ArrayList<UndirectedGraphNode>();
      for (UndirectedGraphNode readyToBeCopyNode : temp.neighbors) {
        //检查是不是已经复制过
        if (relationMap.containsKey(readyToBeCopyNode)) {
          lists.add(relationMap.get(readyToBeCopyNode));
        } else {
          UndirectedGraphNode copyNode = new UndirectedGraphNode(temp.label);
          relationMap.put(readyToBeCopyNode, copyNode);
          lists.add(copyNode);
          stack.push(readyToBeCopyNode);
        }
      }
      relationMap.get(temp).neighbors = lists;
    }

    return head;
  }

  //递归实现
  public static UndirectedGraphNode cloneGraph_014(UndirectedGraphNode node) {
    Map<UndirectedGraphNode, UndirectedGraphNode> relationMap = new HashMap<>();

    return relationMap.get(node);
  }

  private static UndirectedGraphNode cloneGraph(
      Map<UndirectedGraphNode, UndirectedGraphNode> relationMap,
      UndirectedGraphNode node) {

    if (relationMap.containsKey(node)) {
      return relationMap.get(node);
    }

    UndirectedGraphNode tempNode = new UndirectedGraphNode(node.label);
    relationMap.put(node, tempNode);
    ArrayList<UndirectedGraphNode> lists = new ArrayList<UndirectedGraphNode>();
    for (UndirectedGraphNode readyToBeCopyNode : node.neighbors) {
      if (relationMap.containsKey(readyToBeCopyNode)) {
        lists.add(relationMap.get(readyToBeCopyNode));
      } else {
        lists.add(cloneGraph(relationMap, readyToBeCopyNode));
      }
    }

    tempNode.neighbors = lists;

    return tempNode;
  }

  //  Given a 2D board containing'X'and'O', capture all regions surrounded by'X'.
//  A region is captured by flipping all'O's into'X's in that surrounded region .
//      For example,
//  X X X X
//  X O O X
//  X X O X
//  X O X X
//
//  After running your function, the board should be:
//  X X X X
//  X X X X
//  X X X X
//  X O X X
//  /*
// * 所有与四条边相连的O都保留，其他O都变为X
// * 遍历四条边上的O，并深度遍历与其相连的O，将这些O都转为*
// * 将剩余的O变为X
// * 将剩余的*变为O
///
  public static void solve_015(char[][] board) {

  }


  //  Given a binary tree containing digits from0-9only, each root-to-leaf path could represent a number.
//  An example is the root-to-leaf path1->2->3which represents the number123.
//  Find the total sum of all root-to-leaf numbers.
//  For example,
//        1
//      / \
//      2   3
//
//  The root-to-leaf path1->2represents the number12.
//  The root-to-leaf path1->3represents the number13.
//  Return the sum = 12 + 13 =25.
  public static int sumNumbers_016(TreeNode root) {
    if (root == null) {
      return -1;
    }
    if (root.left == null && root.right == null) {
      return root.val;
    }

    LinkedList<Integer> pathList = new LinkedList<>();
    List<Integer> resultList = new ArrayList<>();

    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    TreeNode currentTreeNode;
    TreeNode lastPrin = root;
    pathList.addFirst(root.val);

    while (!stack.isEmpty()) {
      currentTreeNode = stack.peek();

      if (currentTreeNode.left != null && lastPrin != currentTreeNode.left
          && lastPrin != currentTreeNode.right) {
        stack.push(currentTreeNode.left);
        pathList.addFirst(currentTreeNode.left.val);
      } else if (currentTreeNode.right != null && currentTreeNode.right != lastPrin) {
        stack.push(currentTreeNode.right);
        pathList.addFirst(currentTreeNode.right.val);
      } else {
        currentTreeNode = stack.pop();
        if (lastPrin != currentTreeNode.left && lastPrin != currentTreeNode.right) {
          resultList.add(getPathValue(pathList));
        }
        lastPrin = currentTreeNode;
        System.out.println(currentTreeNode.val + " ");
        pathList.removeFirst();
      }
    }

    int result = 0;
    for (int i = 0; i < resultList.size(); i++) {
      result += resultList.get(i);
    }

    return result;
  }

  public static int getPathValue(LinkedList<Integer> pathList) {
    int result = 0;
    for (int i = 0; i < pathList.size(); i++) {
      result += pathList.get(i) * (Math.round(Math.pow(10, i)));
    }
    return result;
  }

  //先序遍历的思想(根左右)+数字求和(每一层都比上层和*10+当前根节点的值)
  public static int sumNumbers_017(TreeNode root) {
    int sum = 0;
    if (root == null) {
      return sum;
    }

    return preorderSumNumbers(root, sum);
  }

  public static int preorderSumNumbers(TreeNode root, int sum) {
    if (root == null) {
      return 0;
    }

    sum += sum * 10 + root.val;
    if (root.left == null && root.right == null) {
      return sum;
    }
    return preorderSumNumbers(root.left, sum) + preorderSumNumbers(root.right, sum);
  }
}
