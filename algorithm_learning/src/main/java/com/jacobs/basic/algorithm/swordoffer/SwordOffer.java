package com.jacobs.basic.algorithm.swordoffer;

import com.jacobs.basic.algorithm.TreeNode;
import com.jacobs.basic.models.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * @author lichao
 * @date 2018/04/04
 */
public class SwordOffer {

    public static void main(String[] args) {
//        System.out.println(Find(7,
//                new int[][]{{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}}));
        //reOrderArray(new int[]{1, 2, 3, 4, 5});
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        //System.out.println(FindKthToTail(head, 3));
        //System.out.println(ReverseList(head));
        System.out.println(IsPopOrder(new int[]{1, 2, 3, 4, 5}, new int[]{4, 3, 5, 1, 2}));
    }

    //    在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
//    请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
    public static boolean Find(int target, int[][] array) {
        if (array == null || array.length == 0 || array[0].length == 0) {
            return false;
        }

        int row = array.length;
        int col = array[0].length;
        int rowIndex = 0;
        int colIndex = col - 1;

        //从右上角开始
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

    //输入一个链表，从尾到头打印链表每个节点的值。
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

    //    把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
//    输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
//    例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
//    NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
    //采用二分法，找出第一个转折点
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
            //左移
            flag = flag << 1;
        }

        return count;
    }

    //    输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
//    所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
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

    //输入一个链表，输出该链表中倒数第k个结点。
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

    //输入一个链表，反转链表后，输出链表的所有元素。
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

    //输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
    public static boolean HasSubtree(TreeNode root1, TreeNode root2) {
        //当Tree1和Tree2都不为零的时候，才进行比较。否则直接返回false
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
        ////如果Tree2已经遍历完了都能对应的上，返回true
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

    //操作给定的二叉树，将其变换为源二叉树的镜像。
    //仔细观察，发现用后序遍历比较合适
    public static void Mirror(TreeNode root) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            return;
        }

        Mirror(root.left);
        Mirror(root.right);
        //transfer
        TreeNode tempNode = root.left;
        root.left = root.right;
        root.right = tempNode;
    }


    //    输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
//    例如，如果输入如下矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
//    则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
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
            //当只有一行一列的情况
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
                //上边
                while (j < endj) {
                    resultList.add(matrix[i][j++]);
                }

                //右边
                while (i < endi) {
                    resultList.add(matrix[i++][j]);
                }

                //下边
                while (j > startj) {
                    resultList.add(matrix[i][j--]);
                }

                //上边,加一的目的是不需要重复的加，只需要等待下一次循环就好
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

    //    输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。
//    假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，序列4，5,3,2,1是该压栈序列对应的一个弹出序列，
//    但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
    public static boolean IsPopOrder(int[] pushA, int[] popA) {
        if (pushA == null || popA == null
                || pushA.length == 0 || popA.length == 0
                || pushA.length != popA.length) {
            return false;
        }

        //压入栈的所有数字均不相等
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
}
