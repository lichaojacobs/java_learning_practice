package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.algorithm.TreeNode;
import com.jacobs.basic.algorithm.leetcode.Problems_06.Interval;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
        //        UndirectedGraphNode node = new ProblemsMedium_09().new UndirectedGraphNode(0);
        //        node.neighbors = new ArrayList<>();
        //        node.neighbors.add(node);
        //        node.neighbors.add(node);

        ProblemsMedium_09 problemsMedium_09 = new ProblemsMedium_09();
        //        problemsMedium_09.cloneGraph(node);
        problemsMedium_09.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8);
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
        if (start == null || end == null || bank == null || start.equals("") || end.equals("")
            || bank.length == 0) {
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

    //   179. Largest Number Given a list of non negative integers, arrange them such that they form the largest number.
    //
    //        Example 1:
    //
    //    Input: [10,2]
    //    Output: "210"
    // 最简单的做法就是排序
    public String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }

        // Get input integers as strings.
        String[] asStrs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            asStrs[i] = String.valueOf(nums[i]);
        }

        // Sort strings according to custom comparator.
        Arrays.sort(asStrs, new LargerNumberComparator());

        // If, after being sorted, the largest number is `0`, the entire number
        // is zero.
        if (asStrs[0].equals("0")) {
            return "0";
        }

        // Build largest number from sorted array.
        String largestNumberStr = new String();
        for (String numAsStr : asStrs) {
            largestNumberStr += numAsStr;
        }

        return largestNumberStr;
    }

    private class LargerNumberComparator implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            String order1 = a + b;
            String order2 = b + a;
            return order2.compareTo(order1);
        }
    }

    //56. Merge Intervals
    //    Given a collection of intervals, merge all overlapping intervals.
    //
    //    Example 1:
    //
    //    Input: [[1,3],[2,6],[8,10],[15,18]]
    //    Output: [[1,6],[8,10],[15,18]]
    //    Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
    //    Example 2:
    //
    //    Input: [[1,4],[4,5]]
    //    Output: [[1,5]]
    //    Explanation: Intervals [1,4] and [4,5] are considerred overlapping.
    public List<Interval> merge(ArrayList<Interval> intervals) {
        List<Interval> results = new ArrayList<>();
        if (intervals == null || intervals.size() == 0) {
            return results;
        }

        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start - o2.start;
            }
        });

        int index = 0;
        while (index < intervals.size()) {
            Interval interval = intervals.get(index);
            int start = interval.start;
            int end = interval.end;
            for (int j = index; j < intervals.size(); j++) {
                Interval tempInterval = intervals.get(j);
                if (tempInterval.start <= end) {
                    end = tempInterval.end > end ? tempInterval.end : end;
                    if (j == intervals.size() - 1) {
                        results.add(new Interval(start, end));
                        index = j + 1;
                    }
                } else {
                    results.add(new Interval(start, end));
                    index = j;
                    if (j == intervals.size() - 1) {
                        results.add(tempInterval);
                        index = j + 1;
                    }
                    break;
                }
            }
        }

        return results;
    }

    // 402. Remove K Digits
    // Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is the smallest possible.
    //
    //        Note:
    //    The length of num is less than 10002 and will be ≥ k.
    //    The given num does not contain any leading zero.
    //        Example 1:
    //
    //    Input: num = "1432219", k = 3
    //    Output: "1219"
    //    Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
    public String removeKdigits(String num, int k) {
        if (num == null || "".equals(num)) {
            return num;
        }
        int digits = num.length() - k;
        char[] stk = new char[num.length()];
        int top = 0;
        // k keeps track of how many characters we can remove
        // if the previous character in stk is larger than the current one
        // then removing it will get a smaller number
        // but we can only do so when k is larger than 0
        for (int i = 0; i < num.length(); ++i) {
            char c = num.charAt(i);
            while (top > 0 && stk[top - 1] > c && k > 0) {
                top -= 1;
                k -= 1;
            }
            stk[top++] = c;
        }
        // find the index of first non-zero digit
        int idx = 0;
        while (idx < digits && stk[idx] == '0') {
            idx++;
        }
        return idx == digits ? "0" : new String(stk, idx, digits - idx);
    }

    // 464. Can I Win
    // In the "100 game," two players take turns adding, to a running total, any integer from 1..10. The player who first causes the running total to reach or exceed 100 wins.
    //
    //        What if we change the game so that players cannot re-use integers?
    //
    //    For example, two players might take turns drawing from a common pool of numbers of 1..15 without replacement until they reach a total >= 100.
    //
    //    Given an integer maxChoosableInteger and another integer desiredTotal, determine if the first player to move can force a win, assuming both players play optimally.
    //
    //    You can always assume that maxChoosableInteger will not be larger than 20 and desiredTotal will not be larger than 300.
    // 就是一个普通的深度搜索的题目，有点像输出一个字符串的全排列的效果
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal <= maxChoosableInteger) {
            return true;
        }

        if (((1 + maxChoosableInteger) * maxChoosableInteger / 2) < desiredTotal) {
            return false;
        }
        return helper(new HashMap(), new boolean[maxChoosableInteger + 1], desiredTotal);
    }

    boolean helper(Map<Integer, Boolean> dp, boolean[] used, int target) {
        if (target <= 0) {
            return false;
        }
        int key = hashCode(used);
        if (dp.containsKey(key)) {
            return dp.get(key);
        }

        for (int i = 1; i < used.length; i++) {
            if (!used[i]) {
                used[i] = true;
                boolean secondPlayerResult = helper(dp, used, target - i);
                used[i] = false;
                //通过递归来区分先后抽数的流程,如果第二个玩家得到的结果失败了说明第一个玩家成功了
                if (!secondPlayerResult) {
                    dp.put(key, true);
                    return true;
                }
            }
        }
        dp.put(key, false);
        return false;
    }

    int hashCode(boolean[] used) {
        int num = 0;
        for (boolean b : used) {
            if (b) {
                num |= 1;
            }
            num <<= 1;
        }
        return num;
    }

    //36. Valid Sudoku
    //Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
    //
    //Each row must contain the digits 1-9 without repetition.
    //Each column must contain the digits 1-9 without repetition.
    //Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.
    //The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            HashSet<Character> rows = new HashSet<Character>();
            HashSet<Character> columns = new HashSet<Character>();
            HashSet<Character> cube = new HashSet<Character>();
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.' && !rows.add(board[i][j])) {
                    return false;
                }
                if (board[j][i] != '.' && !columns.add(board[j][i])) {
                    return false;
                }
                int RowIndex = 3 * (i / 3);
                int ColIndex = 3 * (i % 3);
                if (board[RowIndex + j / 3][ColIndex + j % 3] != '.' && !cube
                    .add(board[RowIndex + j / 3][ColIndex + j % 3])) {
                    return false;
                }
            }
        }
        return true;
    }

    // 39. Combination Sum
    // Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.
    //
    //    The same repeated number may be chosen from candidates unlimited number of times.
    //
    //        Note:
    //
    //    All numbers (including target) will be positive integers.
    //    The solution set must not contain duplicate combinations.
    //    Example 1:
    //
    //    Input: candidates = [2,3,6,7], target = 7,
    //    A solution set is:
    //        [
    //        [7],
    //        [2,2,3]
    //        ]
    // 还是采用全排序的做法，递归的枚举可能的组合
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> resultList = new ArrayList<>();
        if (candidates == null || candidates.length == 0 || target <= 0) {
            return resultList;
        }

        combinationHelper(resultList, new ArrayList<>(), candidates, 0, target);
        return resultList;
    }

    public void combinationHelper(List<List<Integer>> resultList, List<Integer> tempResult, int[] candidates, int start, int target) {
        if (start >= candidates.length || target < 0) {
            return;
        }
        if (target == 0) {
            resultList.add(new ArrayList<>(tempResult));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            tempResult.add(candidates[i]);
            combinationHelper(resultList, tempResult, candidates, i, target - candidates[i]);
            tempResult.remove(tempResult.size() - 1);
        }
    }

    // 40. Combination Sum II
    // Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sums to target.
    //
    //    Each number in candidates may only be used once in the combination.
    //
    //    Note:
    //
    //    All numbers (including target) will be positive integers.
    //    The solution set must not contain duplicate combinations.
    //    Example 1:
    //
    //    Input: candidates = [10,1,2,7,6,1,5], target = 8,
    //    A solution set is:
    //        [
    //        [1, 7],
    //        [1, 2, 5],
    //        [2, 6],
    //        [1, 1, 6]
    //        ]
    //思路是先排序：1,1,2,5,6,7,10，如果前一个位置与该位置相同，要么就两个一起使用，要么就选第1个单独使用，剩下的跳过就行
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> resultList = new ArrayList<>();
        if (candidates == null || candidates.length == 0 || target <= 0) {
            return null;
        }
        //先排序
        Arrays.sort(candidates);
        combinationHelper2(resultList, new ArrayList<>(), candidates, 0, target);
        return resultList;
    }

    public void combinationHelper2(List<List<Integer>> resultList, List<Integer> tempResult, int[] candidates, int start, int target) {
        if (start > candidates.length || target < 0) {
            return;
        }
        if (target == 0) {
            if (!resultList.contains(tempResult)) {
                resultList.add(new ArrayList<>(tempResult));
            }
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            //i > start 表示同一层的循环，需要去重
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            tempResult.add(candidates[i]);
            combinationHelper2(resultList, tempResult, candidates, i + 1, target - candidates[i]);
            tempResult.remove(tempResult.size() - 1);
        }
    }

    private class IntervalComparator implements Comparator<Interval> {

        @Override
        public int compare(Interval a, Interval b) {
            if (a.start > b.start) {
                return 1;
            } else if (a.start == b.start) {
                return 0;
            } else {
                return -1;
            }
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
