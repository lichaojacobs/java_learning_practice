package com.jacobs.basic.algorithm.binarytree;

import com.jacobs.basic.algorithm.TreeNode;
import java.util.HashMap;

/**
 * Created by lichao on 2017/9/5. 在二叉树中找到累加和为指定值的最长路径长度
 *
 * 用哈希表sumMap ，key代表从head开始的一条路径上的累加和出现情况,value值代表这个累加和在路径中最早出现的层数 如果在遍历到
 * cur即节点的时候，我们能够知道从head到cur节点这条路径上的累加和出现情况,那么求以cur节点结尾的累加和 为指定值的最长路径长度就非常容易,所以这里我们采用先序遍历
 */
public class GetMaxLengthSum {

  public static void main(String[] args) {

  }


  public int getMaxLengthSum(TreeNode head, int sum) {
    HashMap<Integer, Integer> sumMap = new HashMap<>();
    sumMap.put(0, 0);//重要
    return preOrder(head, sum, 0, 1, 0, sumMap);
  }

  public int preOrder(TreeNode head, int sum, int preSum, int level, int maxLen,
      HashMap<Integer, Integer> sumMap) {
    if (head == null) {
      return maxLen;
    }

    int curSum = preSum + head.val;
    if (!sumMap.containsKey(curSum)) {
      sumMap.put(curSum, level);
    }

    if (sumMap.containsKey(curSum - sum)) {
      maxLen = Math.max(level - sumMap.get(curSum - sum), maxLen);
    }

    maxLen = preOrder(head.left, sum, curSum, level + 1, maxLen, sumMap);
    maxLen = preOrder(head.right, sum, curSum, level + 1, maxLen, sumMap);

    //这里得注意，返回到父节点的时候记得删除map里面属于父节点的记录，否则会造成路径不通的情况
    if (level == sumMap.get(curSum)) {
      sumMap.remove(curSum);
    }

    return maxLen;
  }

}
