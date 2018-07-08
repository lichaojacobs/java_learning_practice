package com.jacobs.basic.algorithm.leetcode;

import com.jacobs.basic.models.ListNode;

import java.util.*;

/**
 * @author lichao
 * @date 2018/03/23
 */
public class ProblemsMedium_08 {
    public static void main(String[] args) {
        //System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        for (int i : twoSum(new int[]{2, 7, 11, 15}, 9)) {
//            System.out.println(i);
//        }
        //System.out.println(isUgly(10));
//        System.out.println(1 << 2);
//        char[][] board = new char[][]{
//                {'X', 'X', 'X', 'O', 'X', 'O', 'X'},
//                {'X', 'O', 'X', 'O', 'X', 'O', 'O'},
//                {'X', 'X', 'X', 'X', 'X', 'X', 'O'},
//                {'X', 'X', 'X', 'X', 'O', 'X', 'O'},
//                {'X', 'X', 'X', 'X', 'X', 'X', 'O'},
//                {'X', 'X', 'X', 'X', 'X', 'X', 'X'},
//                {'O', 'X', 'X', 'O', 'O', 'O', 'X'},
//        };
//
//        //            11110
////            11010
////            11000
////            00000
//
//        char[][] grid = new char[][]{
//                {'1', '1', '1', '1', '0'},
//                {'1', '1', '0', '1', '0'},
//                {'1', '1', '0', '0', '0'},
//                {'0', '0', '0', '0', '0'},
//        };
//
//        //solve(board);
//        System.out.println(numIslands(grid));
//        System.out.println("done");
//        ladderLength("hit", "cog", Lists.newArrayList("hot", "dot", "dog", "lot", "log", "cog"));
//        System.out.println(singleNonDuplicate(new int[]{3, 3, 7, 7, 10, 11, 11}));
//        System.out.println(numDecodings("12"));
    }

    //    1、与题目Two Sum类似，先将数组nums进行排序，然后从左往右依次枚举，在枚举的过程中左右夹逼；
//
//    2、枚举位置为i，当nums[i]为正数时，结束枚举（因为不可能三个正数之和为0）；另外，要枚举过整数不再重复枚举；
//
//    3、枚举到i时，左、右位置分别用l、r表示，当nums[i]+nums[l]+nums[r]>0，右边界往左夹逼；当nums[i]+nums[l]+nums[r]<0，左边界往右夹逼；当nums[i]+nums[l]+nums[r]==0，则算找到一个，要存起来，再将左边界往右夹逼同时要跳过与之前左边界相同的值，否则会出现重复的三个数。
//
//    4、在步骤3的整个过程中，始终要保证l<r
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();
        if (nums == null || nums.length == 0 || nums.length < 3) {
            return resultList;
        }
        //排序
        Arrays.sort(nums);
        int l = 0;
        int r = 0;
        int[] tempArr = new int[3];
        //从左到右枚举
        for (int i = 0; i < nums.length && nums[i] <= 0; ++i) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                l = i + 1;
                r = nums.length - 1;
                while (l < r) {
                    while (l < r && nums[i] + nums[l] + nums[r] > 0) {
                        --r;
                    }//限制右边界
                    if (l < r && nums[i] + nums[l] + nums[r] == 0) {
                        tempArr[0] = nums[i];
                        tempArr[1] = nums[l];
                        tempArr[2] = nums[r];
                        List<Integer> tempList = new ArrayList<>();
                        for (int j : tempArr) {
                            tempList.add(j);
                        }
                        resultList.add(tempList);
                        while (l < r && nums[l] == tempArr[1]) {
                            ++l;
                        }//限制左边界
                    } else {
                        ++l;
                    }
                }
            }
        }
        return resultList;
    }

    public static int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        int[] resultArr = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                int index = map.get(target - nums[i]);
                if (index == i) {
                    continue;
                }
                resultArr[0] = index > i ? i : index;
                resultArr[1] = index > i ? index : i;
            }
        }

        return resultArr;
    }

    //    Given a linked list, swap every two adjacent nodes and return its head.
//
//    For example,
//    Given 1->2->3->4, you should return the list as 2->1->4->3.
//
//    Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed.
    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = dummy;
        while (current.next != null && current.next.next != null) {
            ListNode first = current.next;
            ListNode second = current.next.next;
            first.next = second.next;
            current.next = second;
            current.next.next = first;
            current = current.next.next;
        }
        return dummy.next;
    }

    // (hard)   For example,
//    Given this linked list: 1->2->3->4->5
//
//    For k = 2, you should return: 2->1->4->3->5
//
//    For k = 3, you should return: 3->2->1->4->5
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k < 2) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode tail = dummy, prev = dummy, temp;
        int count;
        while (true) {
            count = k;
            while (count > 0 && tail != null) {
                count--;
                tail = tail.next;
            }
            if (tail == null) break;//Has reached the end


            head = prev.next;//for next cycle
            // prev-->temp-->...--->....--->tail-->....
            // Delete @temp and insert to the next position of @tail
            // prev-->...-->...-->tail-->head-->...
            // Assign @temp to the next node of @prev
            // prev-->temp-->...-->tail-->...-->...
            // Keep doing until @tail is the next node of @prev
            while (prev.next != tail) {
                temp = prev.next;//Assign
                prev.next = temp.next;//Delete

                temp.next = tail.next;
                tail.next = temp;//Insert

            }

            tail = head;
            prev = head;

        }
        return dummy.next;
    }

    //    Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
    //今日头条一面
//    Example:
//    Input: 1->2->4, 1->3->4
//    Output: 1->1->2->3->4->4
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        ListNode head1 = l1;
        ListNode head2 = l2;

        while (head1 != null && head2 != null) {
            if (head1.val > head2.val) {
                head.next = head2;
                head2 = head2.next;
                head = head.next;
            } else {
                head.next = head1;
                head1 = head1.next;
                head = head.next;
            }
        }

        while (head1 != null) {
            head.next = head1;
            head1 = head1.next;
            head = head.next;
        }

        while (head2 != null) {
            head.next = head2;
            head2 = head2.next;
            head = head.next;
        }

        return dummy.next;
    }


    /**
     * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        return partition(lists, 0, lists.length - 1);
    }

    public ListNode partition(ListNode[] lists, int start, int end) {
        if (start == end) return lists[start];
        else if (start < end) {
            int mid = (start + end) / 2;
            ListNode pre = partition(lists, start, mid - 1);
            ListNode aft = partition(lists, mid + 1, end);
            return merge(pre, aft);
        } else {
            return null;
        }
    }

    public static ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = merge(l1.next, l2);
            return l1;
        } else {
            l2.next = merge(l1, l2.next);
            return l2;
        }
    }
//
//    /**
//     * 合并n条有序的链表，使得新链表与旧链表排序一致（今日头条二面题）
//     * O(n log(n))
//     * 使用归并的思想
//     *
//     * @param arrList
//     */
//    public static void mergeMutiArrayList(List<List<Integer>> arrList) {
//        if (arrList == null || arrList.size() == 0) {
//            return;
//        }
//
//
//    }
//
//    public static List<Integer> partHelper(List<List<Integer>> lists, List<Integer> resultList, int start, int end) {
//        if (start == end) return resultList;
//        while (start < end) {
//            int mid = (start + end) / 2;
//            List<>partHelper(lists, resultList, start, mid);
//            partHelper(lists, resultList, mid + 1, end);
//        }
//    }
//
//    public static List<Integer> mergeList(List<Integer> list1, List<Integer> list2, List<Integer> resultList) {
//        int index1 = 0;
//        int index2 = 0;
//
//        while (index1 != list1.size() && index2 != list2.size()) {
//            if (list1.get(index1) > list2.get(index2)) {
//                resultList.add(list2.get(index2++));
//            } else {
//                resultList.add(list1.get(index1++));
//            }
//        }
//
//        while (index1 != list1.size()) {
//            resultList.add(list1.get(index2++));
//        }
//
//        while (index2 != list2.size()) {
//            resultList.add(list2.get(index1++));
//        }
//
//        return resultList;
//    }


    //    Write a program to find the n-th ugly number.
//
//    Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.
//
//    Note that 1 is typically treated as an ugly number, and n does not exceed 1690.
//    The ugly-number sequence is 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, …
//    because every number can only be divided by 2, 3, 5, one way to look at the sequence is to split the sequence to three groups as below:
//
//            (1) 1×2, 2×2, 3×2, 4×2, 5×2, …
//            (2) 1×3, 2×3, 3×3, 4×3, 5×3, …
//            (3) 1×5, 2×5, 3×5, 4×5, 5×5, …
//    We can find that every subsequence is the ugly-sequence itself (1, 2, 3, 4, 5, …) multiply 2, 3, 5.
//5
//    Then we use similar merge method as merge sort, to get every ugly number from the three subsequence.
//
//    Every step we choose the smallest one, and move one step after,including nums with same value.
    public int nthUglyNumber(int n) {
        int[] uglyNums = new int[n];
        int index2 = 0, index3 = 0, index5 = 0;
        uglyNums[0] = 1;
        for (int i = 1; i < n; i++) {
            uglyNums[i] = Math.min(Math.min(uglyNums[index2] * 2, uglyNums[index3] * 3), uglyNums[index5] * 5);
            if (uglyNums[i] == uglyNums[index2] * 2) {
                index2++;
            }
            if (uglyNums[i] == uglyNums[index3] * 3) {
                index3++;
            }
            if (uglyNums[i] == uglyNums[index5] * 5) {
                index5++;
            }
        }

        return uglyNums[n - 1];
    }

    public static boolean isUgly(int num) {
        if (num <= 0) {
            return false;
        }
        if (num == 1) {
            return true;
        }

        int temp = num;
        while (temp > 1) {
            int yu = 0;
            if ((yu = temp % 2) == 0) {
                temp = temp / 2;
                continue;
            }
            if ((yu = temp % 3) == 0) {
                temp = temp / 3;
                continue;
            }
            if ((yu = temp % 5) == 0) {
                temp = temp / 5;
                continue;
            }

            return false;
        }

        return true;
    }


    //    Given an array of integers sorted in ascending order, find the starting and ending position of a given target value.
//
//    Your algorithm's runtime complexity must be in the order of O(log n).
//
//    If the target is not found in the array, return [-1, -1].
//
//    For example,
//    Given [5, 7, 7, 8, 8, 10] and target value 8,
//            return [3, 4].
    public static int[] searchRange(int[] nums, int target) {
        int[] resultArr = new int[]{-1, -1};
        if (nums == null || nums.length == 0) {
            return resultArr;
        }
        int startIndex = firstGreaterEqual(nums, target);
        if (startIndex == nums.length || nums[startIndex] != target) {
            return new int[]{-1, -1};
        }
        resultArr[0] = startIndex;
        resultArr[1] = firstGreaterEqual(nums, target + 1) - 1;

        return resultArr;
    }

    //找到第一个比目标大的数，总共调用两次函数，一次target，一次target+1
    private static int firstGreaterEqual(int[] A, int target) {
        int low = 0, high = A.length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (A[mid] < target) {
                low = mid + 1;
            } else {
                //可能A[mid]=target，但是现在还不确定是否就是第一个出现target的位置
                high = mid;
            }
        }
        return low;
    }

    //    Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
//
//    For example, given n = 3, a solution set is:
//
//            [
//            "((()))",
//            "(()())",
//            "(())()",
//            "()(())",
//            "()()()"
//            ]
// Instead of adding '(' or ')' every time as in Approach #1, let's only add them when we know it will remain a valid sequence.
// We can do this by keeping track of the number of opening and closing brackets we have placed so far.
//We can start an opening bracket if we still have one (of n) left to place.
// And we can start a closing bracket if it would not exceed the number of opening brackets.
    public List<String> generateParenthesis(int n) {
        List<String> results = new ArrayList<>();
        if (n <= 0) {
            return results;
        }
        backTrack(results, "", 0, 0, n);

        return results;
    }

    public void backTrack(List<String> ans, String cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur);
        }

        //采用空格放置的思路，先放置全部的左括号，通过栈弹出后，塞右括号，只要右括号比左括号少就一直压栈
        //这样能确保所有的情况
        if (open < max) {
            backTrack(ans, cur + '(', open + 1, close, max);
        }
        if (open < close) {
            backTrack(ans, cur + ')', open, close + 1, max);
        }
    }

    //    Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
//
//    If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
//
//    The replacement must be in-place and use only constant extra memory.
//
//    Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
//
//            1,2,3 → 1,3,2
//            3,2,1 → 1,2,3
//            1,1,5 → 1,5,1
    // 给出一种全排列的情况，求出下一个全排列,要求字典序排序，下一个排列得刚好排在当前的后面
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }

        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        //后面因为已经是降序排列了，所以需要整个reverse一下
        int j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //    Given an array of integers, find out whether there are two distinct indices i and j in the array such that the absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.
//
//    Example 1: 做错，想法错误
//
//    Input: nums = [1,2,3,1], k = 3, t = 0
//    Output: true
    public static boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length <= 0 || k <= 0 || t < 0) {
            return false;
        }

        LinkedList<Integer> windowMinIndex = new LinkedList<>();
        int start = 0;
        int end = 0;
        while (end <= nums.length - 1) {
            //超出范围需要从双端队列中淘汰一个
            if (end - start > k) {
                windowMinIndex.pollLast();
                start++;
            }
            if (!windowMinIndex.isEmpty()) {
                int currentMinIndex = windowMinIndex.peekLast();
                if (Math.abs(nums[currentMinIndex] - nums[end]) <= t) {
                    //满足条件
                    return true;
                }
            }
            //如果当前遍历到的数比队列中新加入的数小，则依次弹出
            if (!windowMinIndex.isEmpty() && nums[windowMinIndex.peekFirst()] > nums[end]) {
                windowMinIndex.pollFirst();
            }
            //将数添加到合适的位置
            windowMinIndex.addFirst(end);
            end++;
        }

        return false;
    }

    /**
     * 利用HashMap的分桶原则，buckets的size为t+1，因为如果t=3的话，那么0,1,2都需要分布到相同的桶里面去，
     * 但题意其实是3也应该到相同的桶里去，这样size 应该为t+1，所以buckets size = t+1
     * Integer.MIN_VALUE的考量: Another complication is that negative ints are allowed.
     * A simple num / t just shrinks everything towards 0. Therefore, we can just
     * reposition every element to start from Integer.MIN_VALUE.
     *
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public static boolean containsNearbyAlmostDuplicate1(int[] nums, int k, int t) {
        if (nums == null || nums.length <= 0 || k <= 0 || t < 0) {
            return false;
        }

        Map<Long, Long> bucketMap = new HashMap<>();
        for (int index = 0; index < nums.length; index++) {
            long remappedNum = (long) nums[index] - Integer.MIN_VALUE;
            long bucket = remappedNum / ((long) t + 1);
            if (bucketMap.containsKey(bucket)
                    || (bucketMap.containsKey(bucket - 1) && remappedNum - bucketMap.get(bucket - 1) <= t)
                    || (bucketMap.containsKey(bucket + 1) && bucketMap.get(bucket + 1) - remappedNum <= t)) {
                return true;
            }

            if (bucketMap.entrySet().size() >= k) {
                long lastBucket = ((long) nums[index - k] - Integer.MIN_VALUE) / ((long) t + 1);
                bucketMap.remove(lastBucket);
            }
            bucketMap.put(bucket, remappedNum);
        }

        return false;
    }

    //    Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.
//
//    A region is captured by flipping all 'O's into 'X's in that surrounded region.
//
//            Example:
//
//    X X X X
//    X O O X
//    X X O X
//    X O X X
//    After running your function, the board should be:
//
//    X X X X
//    X X X X
//    X X X X
//    X O X X
//    Explanation:
//
//    Surrounded regions shouldn’t be on the border, which means that any 'O' on the border of the board are not flipped to 'X'.
//    Any 'O' that is not on the border and it is not connected to an 'O' on the border will be flipped to 'X'. Two cells are connected if they are adjacent cells connected horizontally or vertically.
    // 这种方式最后一个测试用例跑不过，显示time limit
    // 究其原因是iswalked 没有起到实际的用处，每次遍历还是要重新标记，
    // 没法一次就标记为已经走过，之后就不需要再走的状态
    public static void solve(char[][] board) {
        if (board == null || board.length <= 1 || board[0].length <= 1) {
            return;
        }

        int row = board.length;
        int column = board[0].length;
        boolean[][] isOnBoard = new boolean[row][column];
        Stack<int[]> stack = new Stack<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (board[i][j] == 'O') {
                    boolean shouldMarkOnBoard = false;
                    stack.push(new int[]{i, j});
                    boolean[][] iswalked = new boolean[row][column];
                    while (!stack.isEmpty()) {
                        int[] arr = stack.pop();
                        int k = arr[0];
                        int m = arr[1];

                        iswalked[k][m] = true;
                        if (isOnBoard[k][m]) {
                            shouldMarkOnBoard = true;
                            stack.clear();
                            break;
                        }
                        if (k == 0 || m == 0 || k == row - 1 || m == column - 1) {
                            isOnBoard[i][j] = true;
                            shouldMarkOnBoard = true;
                            stack.clear();
                            break;
                        }

                        if (k - 1 >= 0) {
                            //上边
                            if (!iswalked[k - 1][m] && board[k - 1][m] == 'O') {
                                stack.push(new int[]{k - 1, m});
                            }
                        }
                        if (k + 1 < row) {
                            //下边
                            if (!iswalked[k + 1][m] && board[k + 1][m] == 'O') {
                                stack.push(new int[]{k + 1, m});
                            }
                        }
                        if (m + 1 < column) {
                            //右边
                            if (!iswalked[k][m + 1] && board[k][m + 1] == 'O') {
                                stack.push(new int[]{k, m + 1});
                            }
                        }
                        if (m - 1 >= 0) {
                            //左边
                            if (!iswalked[k][m - 1] && board[k][m - 1] == 'O') {
                                stack.push(new int[]{k, m - 1});
                            }
                        }
                    }

                    if (shouldMarkOnBoard) {
                        isOnBoard[i][j] = true;
                    } else {
                        board[i][j] = 'X';
                    }
                }
            }
        }
    }

    /**
     * 教科书方法，思路很简单，不需要墨守成规的从左到右，从上到下遍历
     * 而是从根源找起，一次性标记所有与边缘的O相连的O，不用像我那样没有用到isWalked
     *
     * @param board
     */
    public void solve2(char[][] board) {
        if (board == null || board.length == 0) return;

        int row = board.length;
        int col = board[0].length;

        //check first and last col
        for (int i = 0; i < row; i++) {

            if (board[i][0] == 'O') getEmAll(board, i, 1);
            if (board[i][col - 1] == 'O') getEmAll(board, i, col - 2);
        }

        //check first and last  row
        for (int i = 0; i < col; i++) {

            if (board[0][i] == 'O') getEmAll(board, 1, i);
            if (board[row - 1][i] == 'O') getEmAll(board, row - 2, i);
        }


        //Switch all 'O's to 'X's and 'Y's to 'O's
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++)
                if (board[i][j] == 'Y') board[i][j] = 'O';
                else if (board[i][j] == 'O') board[i][j] = 'X';
        }
    }

    public void getEmAll(char[][] board, int row, int col) {

        if (row >= board.length - 1 || row <= 0 || col >= board[0].length - 1 || col <= 0) return;

        if (board[row][col] == 'X' || board[row][col] == 'Y') return;
        if (board[row][col] == 'O') board[row][col] = 'Y';

        getEmAll(board, row + 1, col);
        getEmAll(board, row, col + 1);
        getEmAll(board, row - 1, col);
        getEmAll(board, row, col - 1);

    }

    //    Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
//
//    Example 1:
//
//    Input:
//            11110
//            11010
//            11000
//            00000
//
//    Output: 1 自己做出来的，之前美团面试的时候就想出来了
    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int row = grid.length;
        int column = grid[0].length;
        //深度遍历
        Stack<int[]> stack = new Stack<>();
        //标记是否走过
        boolean[][] iswalked = new boolean[row][column];
        int islandNum = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (iswalked[i][j]) {
                    continue;
                }
                stack.push(new int[]{i, j});
                if (grid[i][j] == '1') {
                    while (!stack.isEmpty()) {
                        int[] item = stack.pop();
                        int x = item[0];
                        int y = item[1];
                        iswalked[x][y] = true;
                        if (grid[x][y] == '1') {
                            if (x > 0 && !iswalked[x - 1][y]) {
                                stack.push(new int[]{x - 1, y});
                            }
                            if (x < row - 1 && !iswalked[x + 1][y]) {
                                stack.push(new int[]{x + 1, y});
                            }
                            if (y > 0 && !iswalked[x][y - 1]) {
                                stack.push(new int[]{x, y - 1});
                            }
                            if (y < column - 1 && !iswalked[x][y + 1]) {
                                stack.push(new int[]{x, y + 1});
                            }
                        }
                    }
                    islandNum++;
                }
            }
        }

        return islandNum;
    }

    //
//    Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:
//    Only one letter can be changed at a time.
//    Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
//    Note:
//    Return 0 if there is no such transformation sequence.
//    All words have the same length.
//    All words contain only lowercase alphabetic characters.
//    You may assume no duplicates in the word list.
//    You may assume beginWord and endWord are non-empty and are not the same.
//    Input:
//    beginWord = "hit",
//    endWord = "cog",
//    wordList = ["hot","dot","dog","lot","log","cog"]
//
//    Output: 5
//
    // 明显的广度遍历，图论里面的单源最短路问题。其思路就是先把起点加到队列中, 然后每次将字典中与队首距离为1的字符串加进队列, 直到最后出队列的是终点字符串
//    Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
//            return its length 5.
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Map<String, Integer> countMap = new HashMap<>();
        HashSet<String> wordSet = new HashSet<>(wordList);
        countMap.put(beginWord, 1);

        while (!queue.isEmpty()) {
            String temp = queue.poll();
            int len = countMap.get(temp);
            if (temp.equals(endWord)) {
                return len;
            }

            char[] chars = temp.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                for (char j = 'a'; j <= 'z'; j++) {
                    char oldVal = chars[i];
                    chars[i] = j;
                    String newTempStr = String.valueOf(chars);
                    //重复出现过的字符就不用再继续了，生成的链路肯定比之前的要长
                    if (!countMap.containsKey(newTempStr) && wordSet.contains(newTempStr)) {
                        queue.offer(newTempStr);
                        countMap.put(newTempStr, len + 1);
                    }

                    //记得换回来，每次只能变更一个字符
                    chars[i] = oldVal;
                }
            }
        }

        return 0;
    }

    //    Given a sorted array consisting of only integers where every element appears twice except for one element which appears once. Find this single element that appears only once.
//
//    Example 1:
//    Input: [1,1,2,3,3,4,4,8,8]
//    Output: 2
//    Example 2:
//    Input: [3,3,7,7,10,11,11]
//    Output: 10
    //Note: Your solution should run in O(log n) time and O(1) space.
    //二分法
    public static int singleNonDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0;
        int end = nums.length - 1;
        while (start >= 0 && end <= nums.length - 1 && start < end) {
            int mid = (start + end) / 2;
            if (nums[mid - 1] == nums[mid]) {
                if ((mid - 1) % 2 != 0) {
                    end = mid - 2;
                } else {
                    start = mid + 1;
                }
            } else if (nums[mid + 1] == nums[mid]) {
                if (mid % 2 != 0) {
                    end = mid - 1;
                } else {
                    start = mid + 2;
                }
            } else {
                return nums[mid];
            }
        }

        //最后肯定只剩下一个元素
        return nums[start];
    }

    //    Note: You may assume there is no extra space or special characters in the input string.
//
//            Example 1:
//    Input: "172.16.254.1"
//
//    Output: "IPv4"
//
//    Explanation: This is a valid IPv4 address, return "IPv4".
//    Example 2:
//    Input: "2001:0db8:85a3:0:0:8A2E:0370:7334"
//
//    Output: "IPv6"
//
//    Explanation: This is a valid IPv6 address, return "IPv6".
//    Example 3:
//    Input: "256.256.256.256"
//
//    Output: "Neither"
//
//    Explanation: This is neither a IPv4 address nor a IPv6 address.
    public String validIPAddress(String IP) {
        if (IP.matches("(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])"))
            return "IPv4";
        if (IP.matches("(([0-9a-fA-F]{1,4}):){7}([0-9a-fA-F]{1,4})")) return "IPv6";
        return "Neither";
    }

    //    A message containing letters from A-Z is being encoded to numbers using the following mapping:
//
//            'A' -> 1
//            'B' -> 2
//            ...
//            'Z' -> 26
//    Given a non-empty string containing only digits, determine the total number of ways to decode it.
//
//            Example 1:
//
//    Input: "12"
//    Output: 2
//    Explanation: It could be decoded as "AB" (1 2) or "L" (12).
//    Example 2:
//
    // 简单的动态规划，只需要
//    Input: "226"
//    Output: 3
//    Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
    public static int numDecodings(String s) {
        int n = s.length();
        if (n == 0) return 0;

        int[] memo = new int[n + 1];
        memo[n] = 1;
        memo[n - 1] = s.charAt(n - 1) != '0' ? 1 : 0;

        for (int i = n - 2; i >= 0; i--)
            if (s.charAt(i) == '0') continue;
            else memo[i] = (Integer.parseInt(s.substring(i, i + 2)) <= 26) ? memo[i + 1] + memo[i + 2] : memo[i + 1];

        return memo[0];
    }

    //hard
//    Beyond that, now the encoded string can also contain the character '*', which can be treated as one of the numbers from 1 to 9.
//
//    Given the encoded message containing digits and the character '*', return the total number of ways to decode it.
//
//    Also, since the answer may be very large, you should return the output mod 109 + 7.
//
//    Example 1:
//    Input: "*"
//    Output: 9
//    Explanation: The encoded message can be decoded to the string: "A", "B", "C", "D", "E", "F", "G", "H", "I".
//    Example 2:
//    Input: "1*"
//    Output: 9 + 9 = 18
//    Note:
//    The length of the input string will fit in range [1, 105].
//    The input string will only contain the character '*' and digits '0' - '9'.
    public int numDecodings_2(String s) {
        /* initial conditions */
        long[] dp = new long[s.length()+1];
        dp[0] = 1;
        if(s.charAt(0) == '0'){
            return 0;
        }
        dp[1] = (s.charAt(0) == '*') ? 9 : 1;

        /* bottom up method */
        for(int i = 2; i <= s.length(); i++){
            char first = s.charAt(i-2);
            char second = s.charAt(i-1);

            // For dp[i-1]
            if(second == '*'){
                dp[i] += 9*dp[i-1];
            }else if(second > '0'){
                dp[i] += dp[i-1];
            }

            // For dp[i-2]
            if(first == '*'){
                if(second == '*'){
                    dp[i] += 15*dp[i-2];
                }else if(second <= '6'){
                    dp[i] += 2*dp[i-2];
                }else{
                    dp[i] += dp[i-2];
                }
            }else if(first == '1' || first == '2'){
                if(second == '*'){
                    if(first == '1'){
                        dp[i] += 9*dp[i-2];
                    }else{ // first == '2'
                        dp[i] += 6*dp[i-2];
                    }
                }else if( ((first-'0')*10 + (second-'0')) <= 26 ){
                    dp[i] += dp[i-2];
                }
            }

            dp[i] %= 1000000007;
        }
        /* Return */
        return (int)dp[s.length()];
    }
}
