package com.jacobs.basic.algorithm.swordoffer;

import com.jacobs.basic.algorithm.TreeNode;
import com.jacobs.basic.algorithm.dp.ChangeCoins;
import com.jacobs.basic.models.ListNode;

import java.util.*;

/**
 * @author lichao
 * @date 2018/04/04
 */
public class SwordOffer {

    public static void main(String[] args) {
        // System.out.println(Find(7,
        // new int[][]{{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}}));
        // reOrderArray(new int[]{1, 2, 3, 4, 5});
        // ListNode head = new ListNode(1);
        // head.next = new ListNode(2);
        // head.next.next = new ListNode(3);
        // System.out.println(FindKthToTail(head, 3));
        // System.out.println(ReverseList(head));
        // System.out.println(IsPopOrder(new int[]{1, 2, 3, 4, 5}, new int[]{4, 3, 5, 1,
        // 2}));

        // System.out.println(VerifySquenceOfBST(new int[]{4}));

        // find path
        // TreeNode root = new TreeNode(1);
        // root.left = new TreeNode(2);
        // root.right = new TreeNode(3);
        // root.left.left = new TreeNode(4);
        // root.left.right = new TreeNode(5);
        // root.right.left = new TreeNode(3);
        // System.out.println(FindPath(root, 7));
        // System.out.println(Permutation("abcc"));

        // System.out.println(FindGreatestSumOfSubArray(new int[]{6, -3, -2, 7, -15, 1,
        // 2, 2}));
    }

    // 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
    // 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
    public static boolean Find(int target, int[][] array) {
        if (array == null || array.length == 0 || array[0].length == 0) {
            return false;
        }

        int row = array.length;
        int col = array[0].length;
        int rowIndex = 0;
        int colIndex = col - 1;

        // 从右上角开始
        while (rowIndex < row && colIndex < col && rowIndex >= 0 && colIndex >= 0) {
            if (array[rowIndex][colIndex] > target) {
                colIndex--;
            } else if (array[rowIndex][colIndex] < target) {
                rowIndex++;
            } else {
                return true;
            }
        }

        return false;
    }

    // 输入一个链表，从尾到头打印链表每个节点的值。
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> resultList = new ArrayList<>();
        if (listNode == null) {
            return resultList;
        }
        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }

        while (!stack.isEmpty()) {
            resultList.add(stack.pop());
        }

        return resultList;
    }

    // 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
    // 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
    // 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
    // NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
    // 采用二分法，找出第一个转折点
    public static int minNumberInRotateArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] > array[high]) {
                low = mid + 1;
            } else if (array[mid] < array[high]) {
                high = mid;
            } else {
                high--;
            }
        }

        return array[low];
    }

    public int NumberOf1(int n) {
        int count = 0;
        int flag = 1;
        while (flag != 0) {
            if ((flag & n) != 0) {
                count++;
            }
            // 左移
            flag = flag << 1;
        }

        return count;
    }

    // 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
    // 所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
    public static void reOrderArray(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        int oddIndex = 0;
        int index = 1;
        int len = array.length;

        while (index < len && oddIndex < len) {
            while (oddIndex < len && array[oddIndex] % 2 != 0) {
                oddIndex++;
                index = oddIndex + 1;
            }
            if (oddIndex >= len) {
                return;
            }
            if (array[index] % 2 == 0) {
                index++;
            } else {
                int shiftIndex = index;
                int oddVal = array[index];
                while (shiftIndex > oddIndex) {
                    array[shiftIndex] = array[shiftIndex - 1];
                    shiftIndex--;
                }
                array[oddIndex++] = oddVal;
                index++;
            }
        }
    }

    // 输入一个链表，输出该链表中倒数第k个结点。
    public static ListNode FindKthToTail(ListNode head, int k) {
        if (head == null || k < 0) {
            return null;
        }

        ListNode curr = head;
        ListNode aft = head;

        int index = 1;
        while (aft.next != null && index < k) {
            aft = aft.next;
            index++;
        }

        if (index < k) {
            return null;
        } else {
            while (aft.next != null) {
                curr = curr.next;
                aft = aft.next;
            }
        }

        return curr;
    }

    // 输入一个链表，反转链表后，输出链表的所有元素。
    public static ListNode ReverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null;
        ListNode curr = head;
        ListNode aft;

        while (curr != null) {
            aft = curr.next;
            curr.next = pre;
            pre = curr;
            curr = aft;
        }

        return pre;
    }

    // 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
    public static boolean HasSubtree(TreeNode root1, TreeNode root2) {
        // 当Tree1和Tree2都不为零的时候，才进行比较。否则直接返回false
        if (root1 == null || root2 == null) {
            return false;
        }
        boolean hasSubTree = false;
        if (root1.val == root2.val) {
            hasSubTree = subTreeHelper(root1, root2);
        }
        if (!hasSubTree) {
            hasSubTree = HasSubtree(root1.left, root2);
            if (!hasSubTree) {
                hasSubTree = HasSubtree(root1.right, root2);
            }
        }

        return hasSubTree;
    }

    public static boolean subTreeHelper(TreeNode root1, TreeNode root2) {
        //// 如果Tree2已经遍历完了都能对应的上，返回true
        if (root2 == null) {
            return true;
        }
        // //如果Tree2还没有遍历完，Tree1却遍历完了。返回false
        if (root1 == null) {
            return false;
        }

        return (root1.val == root2.val) && subTreeHelper(root1.left, root2.left)
                && subTreeHelper(root1.right, root2.right);
    }

    // 操作给定的二叉树，将其变换为源二叉树的镜像。
    // 仔细观察，发现用后序遍历比较合适
    public static void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            return;
        }

        Mirror(root.left);
        Mirror(root.right);
        // transfer
        TreeNode tempNode = root.left;
        root.left = root.right;
        root.right = tempNode;
    }

    // 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
    // 例如，如果输入如下矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
    // 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
    public ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> resultList = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return resultList;
        }

        if (matrix.length == 1 && matrix[0].length == 1) {
            resultList.add(matrix[0][0]);
            return resultList;
        }

        int starti = 0;
        int startj = 0;
        int endi = matrix.length - 1;
        int endj = matrix[0].length - 1;
        while (starti <= endi && startj <= endj) {
            // 当只有一行一列的情况
            if (starti == endi) {
                for (int j = startj; j <= endj; j++) {
                    resultList.add(matrix[starti][j]);
                }
                return resultList;
            } else if (startj == endj) {
                for (int i = starti; i <= endi; i++) {
                    resultList.add(matrix[i][startj]);
                }
                return resultList;
            } else {
                int i = starti;
                int j = startj;
                // 上边
                while (j < endj) {
                    resultList.add(matrix[i][j++]);
                }

                // 右边
                while (i < endi) {
                    resultList.add(matrix[i++][j]);
                }

                // 下边
                while (j > startj) {
                    resultList.add(matrix[i][j--]);
                }

                // 上边,加一的目的是不需要重复的加，只需要等待下一次循环就好
                while (i > starti) {
                    resultList.add(matrix[i--][j]);
                }

                starti++;
                startj++;
                endi--;
                endj--;
            }
        }

        return resultList;
    }

    // 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。
    // 假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，序列4，5,3,2,1是该压栈序列对应的一个弹出序列，
    // 但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
    public static boolean IsPopOrder(int[] pushA, int[] popA) {
        if (pushA == null || popA == null || pushA.length == 0 || popA.length == 0 || pushA.length != popA.length) {
            return false;
        }

        // 压入栈的所有数字均不相等
        Stack<Integer> stack = new Stack<>();
        int popIndex = 0;
        for (int i = 0; i < pushA.length; i++) {
            if (!stack.isEmpty() && stack.peek() == popA[popIndex]) {
                stack.pop();
                popIndex++;
            }
            stack.push(pushA[i]);
        }

        while (!stack.isEmpty()) {
            if (stack.peek() != popA[popIndex]) {
                return false;
            }
            stack.pop();
            popIndex++;
        }

        return true;
    }

    // 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
    public static ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> results = new ArrayList<>();
        if (root == null)
            return results;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            results.add(temp.val);

            if (temp.left != null)
                queue.add(temp.left);

            if (temp.right != null)
                queue.add(temp.right);
        }

        return results;
    }

    // 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。
    // 假设输入的数组的任意两个数字都互不相同。
    public static boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) {
            return false;
        }

        return isBSTHelper(sequence, 0, sequence.length - 1);
    }

    public static boolean isBSTHelper(int[] sequence, int start, int end) {
        if (start >= end) {
            return true;
        }

        // 用来标记是否已经找到第一个大于头节点数的节点，此节点是左右分支的分界点
        boolean firstLarge = false;
        int endIndex = start;
        for (int i = start; i < end; i++) {
            if (sequence[i] > sequence[end]) {
                if (!firstLarge) {
                    firstLarge = true;
                    // 如果为负数说明此时只有右子树
                    endIndex = i - 1 > 0 ? i - 1 : 0;
                }
            } else {
                // 因为题目说明了里面所有的数都不相同，当在分枝上头节点的右边找到了比头节点小的数，此时直接返回false
                if (firstLarge) {
                    return false;
                }
            }
        }

        return isBSTHelper(sequence, start, endIndex) && isBSTHelper(sequence, endIndex, end - 1);
    }

    // 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
    // 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
    public static ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> resultList = new ArrayList<>();
        if (root == null) {
            return resultList;
        }

        findAllPathsHelper(root, target, resultList, new ArrayList<>());
        return resultList;
    }

    public static void findAllPathsHelper(TreeNode root, int target, ArrayList<ArrayList<Integer>> resultList,
                                          ArrayList<Integer> tempResult) {
        if (root == null) {
            return;
        }

        tempResult.add(root.val);
        // 从根节点到当前节点的总和等于target，并且当前节点必须是叶子节点
        if (root.val == target && root.left == null && root.right == null) {
            resultList.add(new ArrayList<>(tempResult));
        }
        findAllPathsHelper(root.left, target - root.val, resultList, tempResult);
        findAllPathsHelper(root.right, target - root.val, resultList, tempResult);
        // 返回上一层得移除记录
        tempResult.remove(tempResult.size() - 1);
    }

    // 给定数组，arr[i]=k,代表可以从位置i向右跳1~k个距离。
    // 如果从位置0出发，返回最少跳几次能跳到arr最后的位置上
    public static int jump(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int jump = 0;// 总共需要跳的次数
        int curr = 0;// 当前如果要跳，能达到的最远位置
        int next = 0;// 下一次跳能达到的最大位置

        for (int i = 0; i < arr.length; i++) {
            if (curr < i) {
                jump++;
                curr = next;
            }
            next = Math.max(next, i + arr[i]);
        }

        return jump;
    }

    // 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
    // 要求不能创建任何新的结点，只能调整树中结点指针的指向。

    // 方法一：使用容器
    public static TreeNode Convert1(TreeNode pRootOfTree) {
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        inOrderToQueue(pRootOfTree, nodeQueue);
        if (nodeQueue.isEmpty()) {
            return pRootOfTree;
        }

        pRootOfTree = nodeQueue.poll();
        TreeNode pre = pRootOfTree;
        pre.left = null;
        TreeNode cur = null;
        while (!nodeQueue.isEmpty()) {
            cur = nodeQueue.poll();
            pre.right = cur;
            cur.left = pre;
            pre = cur;
        }

        pre.right = null;
        return pRootOfTree;
    }

    // 按照中序遍历将节点塞入队列
    public static void inOrderToQueue(TreeNode head, Queue<TreeNode> queue) {
        if (head == null) {
            return;
        }

        inOrderToQueue(head.left, queue);
        queue.add(head);
        inOrderToQueue(head.right, queue);
    }

    // 不用容器，使用递归解决问题
    public static TreeNode Convert2(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }

        TreeNode last = process(pRootOfTree);
        pRootOfTree = last.right;
        last.right = null;
        return pRootOfTree;
    }

    public static TreeNode process(TreeNode head) {
        if (head == null) {
            return null;
        }

        TreeNode leftE = process(head.left);// 左边结束
        TreeNode rightE = process(head.right);// 右边结束
        TreeNode leftS = leftE != null ? leftE.right : null;// 左边开始
        TreeNode rightS = rightE != null ? rightE.right : null;// 右边开始
        if (leftE != null && rightE != null) {
            leftE.right = head;
            head.left = leftE;
            head.right = rightS;
            rightS.left = head;
            rightE.right = leftS;
            return rightE;
        } else if (leftE != null) {
            leftE.right = head;
            head.left = leftE;
            head.right = leftS;
            return head;
        } else if (rightE != null) {
            head.right = rightS;
            rightS.left = head;
            rightE.right = head;
            return rightE;
        } else {
            head.right = head;
            return head;
        }
    }

    /**
     * 字符串的排列 输入一个字符串,按字典序打印出该字符串中字符的所有排列。
     * 例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
     * <p>
     * 输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
     *
     * @param str
     * @return
     */
    public static ArrayList<String> Permutation(String str) {
        ArrayList<String> resultList = new ArrayList<>();
        if (str == null || str.equals("")) {
            return resultList;
        }

        permutationHelper(resultList, str.toCharArray(), 0, str.length() - 1);
        return resultList;
    }

    public static void permutationHelper(ArrayList<String> resultList, char[] charArr, int start, int end) {
        if (start >= end) {
            StringBuilder stringBuilder = new StringBuilder();
            for (char s : charArr) {
                stringBuilder.append(s);
            }
            resultList.add(stringBuilder.toString());
            return;
        }

        for (int i = start; i <= end; i++) {
            if (i != start && charArr[i] == charArr[start]) {
                continue;
            }
            swap(charArr, start, i);
            permutationHelper(resultList, charArr, start + 1, end);
            swap(charArr, start, i);
        }
    }

    public static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。
    // 今天测试组开完会后,他又发话了:
    // 在古老的一维模式识别中,常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。
    // 但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。
    // 你会不会被他忽悠住？(子向量的长度至少是1)
    public static int FindGreatestSumOfSubArray(int[] array) {
        if (array.length == 0) {
            return array[0];
        }
        int maxSubArr = Integer.MIN_VALUE;
        // 开始不能从索引0开始，就假设从索引0的左边一个位置开始，开始的值都为0，这样对正负都是没有影响的
        int minSubArr = 0;
        int totalSubArr = 0;

        for (int i = 0; i < array.length; i++) {
            totalSubArr += array[i];
            maxSubArr = Math.max(totalSubArr - minSubArr, maxSubArr);
            minSubArr = Math.min(totalSubArr, minSubArr);
        }

        return maxSubArr;
    }

    // 求出1~13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？
    // 为此他特别数了一下1~13中包含1的数字有1、10、11、12、13因此共出现6次,但是对于后面问题他就没辙了。
    // ACMer希望你们帮帮他,并把问题更加普遍化,可以很快的求出任意非负整数区间中1出现的次数
    // 解答：http://www.cnblogs.com/nailperry/p/4752987.html
    public static int NumberOfXBetween1AndN_Solution(int n, int x) {
        if (n < 0 || x < 1 || x > 9)
            return 0;
        int high, low, curr, tmp, i = 1;
        high = n;
        int total = 0;
        while (high != 0) {
            high = n / (int) Math.pow(10, i);// 获取第i位的高位
            tmp = n % (int) Math.pow(10, i);
            curr = tmp / (int) Math.pow(10, i - 1);// 获取第i位
            low = tmp % (int) Math.pow(10, i - 1);// 获取第i位的低位
            if (curr == x) {
                total += high * (int) Math.pow(10, i - 1) + low + 1;
            } else if (curr < x) {
                total += high * (int) Math.pow(10, i - 1);
            } else {
                total += (high + 1) * (int) Math.pow(10, i - 1);
            }
            i++;
        }
        return total;
    }

    // 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
    // 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
    public static String PrintMinNumber(int[] numbers) {
        int n;
        String s = "";
        ArrayList<Integer> list = new ArrayList<Integer>();
        n = numbers.length;

        for (int i = 0; i < n; i++) {
            list.add(numbers[i]);// 将数组放入arrayList中
        }
        // 实现了Comparator接口的compare方法，将集合元素按照compare方法的规则进行排序
        Collections.sort(list, new Comparator<Integer>() {

            @Override
            public int compare(Integer str1, Integer str2) {
                String s1 = str1 + "" + str2;
                String s2 = str2 + "" + str1;

                return s1.compareTo(s2);
            }
        });

        for (int j : list) {
            s += j;
        }
        return s;
    }

    // 数组找连续的subarray，和等于K，给出存在几个。
    public static int getSubArrayEqualsKNum(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int sum = 0;
        int result = 0;
        Map<Integer, Integer> sumMap = new HashMap<>();
        sumMap.put(0, 1);
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (sumMap.containsKey(sum - k)) {
                result += sumMap.get(sum - k);
            }
            sumMap.put(sum, sumMap.getOrDefault(sum, 0) + 1);
        }
        return result;
    }

    // Given an array nums of n integers, are there elements a, b, c in nums such
    // that a + b + c = 0? Find all unique triplets in the array which gives the sum
    // of zero.
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();
        if (nums == null || nums.length == 0 || nums.length < 3) {
            return resultList;
        }
        // 排序
        Arrays.sort(nums);
        int l = 0;
        int r = 0;
        int[] tempArr = new int[3];
        // 从左到右枚举
        for (int i = 0; i < nums.length && nums[i] <= 0; ++i) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                l = i + 1;
                r = nums.length - 1;
                while (l < r) {
                    while (l < r && nums[i] + nums[l] + nums[r] > 0) {
                        --r;
                    } // 限制右边界
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
                        } // 限制左边界
                    } else {
                        ++l;
                    }
                }
            }
        }
        return resultList;
    }

    //    把只包含因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含因子7。
//    习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。
    public int GetUglyNumber_Solution(int index) {
        return 0;
    }
}
