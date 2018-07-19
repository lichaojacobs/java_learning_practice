package com.jacobs.basic.algorithm.leetcode;

import com.google.common.collect.Lists;
import com.jacobs.basic.algorithm.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * @author lichao
 * @date 2018/06/19
 */
public class ProblemsMedium_09 {

    public static void main(String[] args) {
        //        System.out.println(compareVersion("0.1", "1.1"));
        //        Set<Integer> integerSet = new HashSet<>();
        //        integerSet.add(1);
        //        integerSet.add(2);
        //        List<Integer> integerList = Lists.newArrayList(integerSet);
        //        System.out.println(integerList);
        UndirectedGraphNode node = new ProblemsMedium_09().new UndirectedGraphNode(0);
        node.neighbors = new ArrayList<>();
        node.neighbors.add(node);
        node.neighbors.add(node);

        ProblemsMedium_09 problemsMedium_09 = new ProblemsMedium_09();
        problemsMedium_09.cloneGraph(node);
    }

    //    Compare two version numbers version1 and version2.
    //    If version1 > version2 return 1; if version1 < version2 return -1;otherwise return 0.
    //
    //    You may assume that the version strings are non-empty and contain only digits and the . character.
    //    The . character does not represent a decimal point and is used to separate number sequences.
    //    For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level revision of the second first-level revision.
    //
    //    Example 1:
    //
    //    Input: version1 = "0.1", version2 = "1.1"
    //    Output: -1
    //    Example 2:
    //
    //    Input: version1 = "1.0.1", version2 = "1"
    //    Output: 1
    //    Example 3:
    //
    //    Input: version1 = "7.5.2.4", version2 = "7.5.3"
    //    Output: -1
    public static int compareVersion(String version1, String version2) {
        String[] v1Strs = version1.split("\\.");
        String[] v2Strs = version2.split("\\.");

        int v1Length = v1Strs.length;
        int v2Length = v2Strs.length;
        int result = 0;
        int index = 0;

        while (index < v1Length && index < v2Length) {
            int minusResult = Integer.valueOf(v1Strs[index]) - Integer.valueOf(v2Strs[index]);
            if (minusResult > 0) {
                result = 1;
            } else if (minusResult < 0) {
                result = -1;
            } else {
                result = 0;
            }
            if (result != 0) {
                return result;
            }

            index++;
        }

        while (result == 0 && index < v1Length) {
            if (Integer.valueOf(v1Strs[index++]) > 0) {
                result = 1;
                break;
            }
        }

        while (result == 0 && index < v2Length) {
            if (Integer.valueOf(v2Strs[index++]) > 0) {
                result = -1;
                break;
            }
        }

        return result;
    }

    //    You are given an array of positive and negative integers. If a number n at an index is positive, then move forward n steps. Conversely, if it's negative (-n), move backward n steps. Assume the first element of the array is forward next to the last element, and the last element is backward next to the first element. Determine if there is a loop in this array. A loop starts and ends at a particular index with more than 1 element along the loop. The loop must be "forward" or "backward'.
    //
    //    Example 1: Given the array [2, -1, 1, 2, 2], there is a loop, from index 0 -> 2 -> 3 -> 0.
    //
    //    Example 2: Given the array [-1, 2], there is no loop.
    //
    //    Note: The given array is guaranteed to contain no element "0".
    //
    //    Can you do it in O(n) time complexity and O(1) space complexity?
    //思路：类似于链表判断是否有环，使用快慢指针的思路，最终如果有环能够相遇
    public boolean circularArrayLoop(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                continue;
            }
            // slow/fast pointer
            int j = i, k = getIndex(i, nums);
            //判断方向是否改变，如果改变也不是环
            while (nums[k] * nums[i] > 0 && nums[getIndex(k, nums)] * nums[i] > 0) {
                if (j == k) {
                    // check for loop with only one element
                    if (j == getIndex(j, nums)) {
                        break;
                    }
                    return true;
                }
                j = getIndex(j, nums);
                k = getIndex(getIndex(k, nums), nums);
            }
            // loop not found, set all element along the way to 0
            j = i;
            int val = nums[i];
            while (nums[j] * val > 0) {
                int next = getIndex(j, nums);
                nums[j] = 0;
                j = next;
            }
        }
        return false;
    }

    public int getIndex(int index, int[] nums) {
        int n = nums.length;
        return index + nums[index] >= 0 ? (index + nums[index]) % n : n + ((index + nums[index]) % n);
    }

    //    A gene string can be represented by an 8-character long string, with choices from "A", "C", "G", "T".
    //
    //    Suppose we need to investigate about a mutation (mutation from "start" to "end"), where ONE mutation is defined as ONE single character changed in the gene string.
    //
    //    For example, "AACCGGTT" -> "AACCGGTA" is 1 mutation.
    //
    //        Also, there is a given gene "bank", which records all the valid gene mutations. A gene must be in the bank to make it a valid gene string.
    //
    //    Now, given 3 things - start, end, bank, your task is to determine what is the minimum number of mutations needed to mutate from "start" to "end". If there is no such a mutation, return -1.
    //
    //    Note:
    //
    //    Starting point is assumed to be valid, so it might not be included in the bank.
    //    If multiple mutations are needed, all mutations during in the sequence must be valid.
    //    You may assume start and end string is not the same.
    //        Example 1:
    //
    //    start: "AACCGGTT"
    //    end:   "AACCGGTA"
    //    bank: ["AACCGGTA"]
    //
    //        return: 1
    //典型的深度遍历，求最短路径问题
    public int minMutation(String start, String end, String[] bank) {
        if (start == null || end == null || bank == null || start.equals("") || end.equals("") || bank.length == 0) {
            return -1;
        }

        char[] mutations = new char[]{'A', 'C', 'G', 'T'};
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> countMap = new HashMap<>();
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        queue.offer(start);
        countMap.put(start, 0);

        while (!queue.isEmpty()) {
            String popStr = queue.poll();
            int len = countMap.get(popStr);
            if (popStr.equals(end)) {
                return len;
            }
            char[] popCharArr = popStr.toCharArray();
            for (int i = 0; i < popCharArr.length; i++) {
                for (int j = 0; j < mutations.length; j++) {
                    if (mutations[j] == popCharArr[i]) {
                        continue;
                    }
                    char oldVal = popCharArr[i];
                    popCharArr[i] = mutations[j];
                    String newTempStr = String.valueOf(popCharArr);
                    if (!countMap.containsKey(newTempStr) && bankSet.contains(newTempStr)) {
                        queue.offer(newTempStr);
                        countMap.put(newTempStr, len + 1);
                    }

                    //记得换回来，每次只能变更一个字符
                    popCharArr[i] = oldVal;
                }
            }
        }

        return -1;
    }

    //    Given a list of non-negative numbers and a target integer k, write a function to check if the array has a continuous subarray of size at least 2 that sums up to the multiple of k, that is, sums up to n*k where n is also an integer.
    //
    //        Example 1:
    //    Input: [23, 2, 4, 6, 7],  k=6
    //    Output: True
    //    Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.
    //    Example 2:
    //    Input: [23, 2, 6, 4, 7],  k=6
    //    Output: True
    //    Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5 and sums up to 42.
    // 还是利用s[i,j]= s[0,j]-s[0,i]的概念，这里用到了余数的思想，如果上一个s[i] 余数和下一个s [j]余数相同，
    // 说明i到j 之间 至少加了一倍的k的值
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int runningSum = 0;
        for (int i = 0; i < nums.length; i++) {
            runningSum += nums[i];
            if (k != 0) {
                runningSum %= k;
            }
            Integer prev = map.get(runningSum);
            if (prev != null) {
                if (i - prev > 1) {
                    return true;
                }
            } else {
                map.put(runningSum, i);
            }
        }
        return false;
    }

    //    Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.
    //
    //        Example 1:
    //    Input:nums = [1,1,1], k = 2
    //    Output: 2
    //    Note:
    //    The length of the array is in range [1, 20,000].
    //    The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int result = 0;
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            Integer target = map.get(sum - k);
            if (target != null) {
                result += target;
            }
            if (map.containsKey(sum)) {
                map.put(sum, map.get(sum) + 1);
            } else {
                map.put(sum, 1);
            }
        }

        return result;
    }


    //Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }

        Map<UndirectedGraphNode, UndirectedGraphNode> graphMapping = new HashMap();
        Stack<UndirectedGraphNode> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            UndirectedGraphNode preNode = stack.pop();
            UndirectedGraphNode clonedGraph;
            if (!graphMapping.containsKey(preNode)) {
                clonedGraph = new UndirectedGraphNode(preNode.label);
                graphMapping.put(preNode, clonedGraph);
            } else {
                clonedGraph = graphMapping.get(preNode);
            }
            //如果克隆的临接结点有neighbors说明上一次已经克隆过了，直接跳过即可
            if (clonedGraph.neighbors.size() != 0) {
                continue;
            }
            for (int i = 0; i < preNode.neighbors.size(); i++) {
                UndirectedGraphNode neighborNode = preNode.neighbors.get(i);
                if (graphMapping.containsKey(neighborNode)) {
                    clonedGraph.neighbors.add(graphMapping.get(neighborNode));
                } else {
                    UndirectedGraphNode clonedNeighborNode = new UndirectedGraphNode(neighborNode.label);
                    graphMapping.put(neighborNode, clonedNeighborNode);
                    clonedGraph.neighbors.add(clonedNeighborNode);
                }
                stack.push(neighborNode);
            }
        }

        return graphMapping.get(node);
    }


    //Given the root node of a binary search tree (BST) and a value to be inserted into the tree,
    // insert the value into the BST. Return the root node of the BST after the insertion.
    // It is guaranteed that the new value does not exist in the original BST.
    //思路：采用递归的思路，不停的分叉，root.val?val的时候往左，否则往右
    //终结条件：右孩子为空，且root.val <val 或者 左孩子为空且root.val > val
    public TreeNode insertIntoBST(TreeNode root, int val) {

        TreeNode preNode = root;
        insertIntoBSTHelper(root, val);
        return preNode;
    }

    public void insertIntoBSTHelper(TreeNode root, int val) {
        if (root.right == null && root.val < val) {
            root.right = new TreeNode(val);
            return;
        }
        if (root.left == null && root.val > val) {
            root.left = new TreeNode(val);
            return;
        }

        if (root.val > val) {
            insertIntoBSTHelper(root.left, val);
        } else {
            insertIntoBSTHelper(root.right, val);
        }
    }

    class UndirectedGraphNode {

        int label;
        List<UndirectedGraphNode> neighbors;

        UndirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<UndirectedGraphNode>();
        }
    }
}
