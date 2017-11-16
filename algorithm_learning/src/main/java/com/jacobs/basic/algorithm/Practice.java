package com.jacobs.basic.algorithm;

import java.util.LinkedList;

/**
 * @author lichao
 * @date 2017/11/10
 */
public class Practice {

  public static void main(String[] args) {
    multiply("12", "123456");
  }


  /**
   * 大数相乘
   *
   * @param numStr1 num1
   * @param numStr2 num2
   */
  public static void multiply(String numStr1, String numStr2) {
    if (numStr1 == null || numStr2 == null) {
      return;
    }

    char[] chars1 = numStr1.toCharArray();
    char[] chars2 = numStr2.toCharArray();

    LinkedList<Integer> resultList = new LinkedList<>();

    int zeroNum = 0;
    for (int i = chars1.length - 1; i >= 0; i--) {
      LinkedList<Integer> tempResultList = getTempResultList(chars1[i], chars2);
      setResultList(resultList, zeroNum, tempResultList);
      zeroNum++;
    }

    for (int i = 0; i < resultList.size(); i++) {
      System.out.print(resultList.get(i));
    }
  }

  private static LinkedList<Integer> getTempResultList(char c, char[] chars2) {
    int up = 0;
    int curr = 0;
    LinkedList<Integer> tempResultList = new LinkedList<>();
    for (int j = chars2.length - 1; j >= 0; j--) {
      curr =
          Integer.valueOf(String.valueOf(c)) * Integer.valueOf(String.valueOf(chars2[j]))
              + up;
      if (curr > 10) {
        up = curr / 10;
        curr = curr % 10;
      } else {
        //注意up每次都得变
        up = 0;
      }
      //加入当前位的值
      tempResultList.addFirst(curr);
    }
    return tempResultList;
  }

  private static void setResultList(LinkedList<Integer> resultList, int index,
      LinkedList<Integer> tempResultList) {
    //每层先补0
    for (int k = 0; k < index; k++) {
      tempResultList.addLast(0);
    }
    //里层循环结束，将和加起来
    int tempi = resultList.size() - 1;
    int tempj = tempResultList.size() - 1;
    int tempUp = 0;
    int tempCurr = 0;
    while (tempi >= 0 && tempj >= 0) {
      tempCurr = resultList.get(tempi) + tempResultList.get(tempj) + tempUp;
      if (tempCurr > 10) {
        tempUp = tempCurr / 10;
        tempCurr = tempCurr % 10;
      } else {
        tempUp = 0;
      }
      resultList.set(tempi, tempCurr);
      tempi--;
      tempj--;
    }
    while (tempj >= 0) {
      resultList.addFirst(tempResultList.get(tempj));
      tempj--;
    }
  }

  /**
   * 二分查找
   */
  public static int binarySearch(int[] arr, int key) {
    if (arr == null || arr.length == 0) {
      return -1;//非法值
    }

    int left = 0;
    int right = arr.length - 1;
    while (left <= right) {
      int mid = (left + right) / 2;
      if (arr[mid] == key) {
        return mid;
      } else if (arr[mid] > key) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }

    return -1;
  }

  public static void mergeSort(int[] arr, int left, int right) {
    if (arr == null || arr.length == 0 || left >= right) {
      return;
    }

    int mid = (left + right) / 2;
    mergeSort(arr, left, mid);
    mergeSort(arr, mid + 1, right);
    merge(arr, left, right, mid);
  }

  public static void merge(int[] arr, int left, int right, int mid) {
    int[] tempArr = new int[right + 1];
    int tempLeft = left;
    int tempMid = mid + 1;

    int index = 0;
    while (tempLeft <= mid && tempMid <= right) {
      if (tempArr[tempLeft] >= tempArr[tempMid]) {
        tempArr[index++] = arr[tempMid++];
      } else {
        tempArr[index++] = arr[tempLeft++];
      }
    }

    if (tempLeft <= mid) {
      tempArr[index++] = arr[tempLeft++];
    }

    if (tempMid <= right) {
      tempArr[index++] = arr[tempMid++];
    }

    //复制到原数组
    for (int i = left; i <= right; i++) {
      arr[i] = tempArr[i];
    }
  }


  /**
   * 获取一棵树中，两个节点的最大距离
   *
   * @param root root 根节点
   * @return distance 最大距离
   */
  public static int getMaxDistance(TreeNode root, int[] res) {
    if (root == null) {
      res[0] = 0;
      return 0;
    }

    int leftMax = getMaxDistance(root.left, res);
    int maxFromLeft = res[0];

    int rightMax = getMaxDistance(root.right, res);
    int maxFromRight = res[0];

    int currNodeMax = maxFromLeft + maxFromRight + 1;
    res[0] = Math.max(leftMax, rightMax) + 1;

    return Math.max(Math.max(leftMax, rightMax), currNodeMax);
  }
}
