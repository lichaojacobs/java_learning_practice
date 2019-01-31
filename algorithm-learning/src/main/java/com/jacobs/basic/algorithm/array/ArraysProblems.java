package com.jacobs.basic.algorithm.array;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lichao on 2016/10/31.
 */
public class ArraysProblems {

  public static void main(String[] args) {
//    int[][] matrix = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
//    rotate(matrix);
    System.out.println(partitalBinarySearch(new int[]{4, 5, 6, 1, 2, 3}, 0));
  }

  public static int[] getMaxWindowNumRes(int[] arr, int w) {
    if (arr == null || arr.length == 0) {
      return null;
    }

    LinkedList<Integer> qmax = new LinkedList<>();
    int[] res = new int[arr.length - w + 1];
    int index = 0;
    for (int i = 0; i < arr.length; i++) {
      while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[i]) {
        qmax.pollLast();
      }
      qmax.addLast(i);
      if (qmax.peekFirst() == i - w) {
        qmax.pollFirst();
      }
      //表示满足记录第一个滑动窗口的标识
      if (i >= w - 1) {
        res[index++] = arr[qmax.peekLast()];
      }
    }

    return res;
  }

  /**
   * 最大值减去最小值小于或者等于参数num的子数组数量。
   *
   * @param arr 数组arr
   * @param num 指定和 num
   */
  public static int getNum(int[] arr, int num) {
    if (arr == null || arr.length == 0) {
      return 0;
    }
    LinkedList<Integer> qmin = new LinkedList<>();
    LinkedList<Integer> qmax = new LinkedList<>();
    int i = 0;
    int j = 0;
    int res = 0;
    while (i < arr.length) {
      while (j < arr.length) {
        while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]) {
          qmin.pollLast();
        }
        qmin.addLast(j);
        while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[j]) {
          qmax.pollLast();
        }
        qmax.addLast(j);
        if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
          break;
        }
        j++;
      }
      res += j - i;
      i++;
    }

    return res;
  }

  /**
   * 转圈打印矩阵
   *
   * @param matrix 矩阵
   */
  public static ArrayList<Integer> spiralOrderPrint(int[][] matrix) {
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

  /**
   * N * N 矩阵顺时针旋转90度，要求额外空间为O（1）
   *
   * @param matrix N*N 矩阵
   */
  public static void rotate(int[][] matrix) {
    int rowStart = 0;
    int rowEnd = matrix.length - 1;
    int colStart = 0;
    int colEnd = matrix[0].length - 1;

    while (rowStart < rowEnd) {
      int times = colEnd - colStart;
      int j = 0;

      while (j < times) {
        int temp1 = matrix[j + rowStart][colEnd];
        matrix[j + rowStart][colEnd] = matrix[rowStart][j + colStart];
        int temp2 = matrix[rowEnd][colEnd - j];
        matrix[rowEnd][colEnd - j] = temp1;
        int temp3 = matrix[rowEnd - j][colStart];
        matrix[rowEnd - j][colStart] = temp2;
        matrix[rowStart][j + colStart] = temp3;
        j++;
      }

      rowStart++;
      colStart++;
      colEnd--;
      rowEnd--;
    }

    printMatrix(matrix);
  }


  /**
   * 之字形打印矩阵 想法：起始坐标: (tR,tC) 从左到右，如果到了最后一列，就从最后一列往下， 另一个坐标(dR,dC) 从上到下，如果到了最后一行，就从最后一行往右，直至最后一个坐标
   * 期间，他们的连线就是需要打印的路径，用一个标志标志从上或者从下开始打印
   *
   * @param matrix 矩阵
   */
  public static void printZigZag(int[][] matrix) {
    int tR = 0;
    int tC = 0;
    int dR = 0;
    int dC = 0;
    int endRow = matrix.length - 1;
    int endCol = matrix[0].length - 1;
    boolean fromUp = false;

    while (tR != endRow + 1) {
      if (fromUp) {
        while (tR != dR + 1) {
          System.out.print(matrix[tR++][tC--] + " ");
        }
      } else {
        while (dR != tR - 1) {
          System.out.print(matrix[dR--][dC++] + " ");
        }
      }
      tR = tC == endCol ? tR + 1 : tR;
      tC = tC == endCol ? tC : tC + 1;
      dR = dR == endRow ? dR : dR + 1;
      dC = dR == endRow ? dC + 1 : dC;
    }
  }

  public static void printMatrix(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        System.out.print(matrix[i][j] + ", ");
      }
      System.out.println();
    }
  }


  /**
   * 给定一个double类型的数组arr,其中的元素可正可以负可为0，返回子数组累乘的最大乘积。例如，arr=[-2.5,4,0,3,0.5,8,-1] <p>
   * 子数组[3,0.5,8]累乘可获得最大乘积12，所以返回12 <p> 思路：以i结尾的最大子数组乘积可能来自以下三种情况： <p> 1) max*arr[i] ,
   * max表示以arr[i-1]结尾的最大子数组乘积，这种情况是有可能的如[3,4,5] <p> 2) min*arr[i], min表示以arr[i-1]结尾的最小子数组乘积 <p>
   * 3）arr[i]，可能仅仅是arr[i]的值就能达到最大
   *
   * @param arr 数组arr
   * @return 最大累乘积
   */
  public static double maxProductArray(double[] arr) {
    if (arr == null || arr.length == 0) {
      return -1;//非法值
    }

    //全局最大的结果
    double res = arr[0];
    double max = arr[0];
    double min = arr[0];
    //用来暂存当前位置的最大最小值，不一定是最大或最小
    double maxEnd;
    double minEnd;

    for (int i = 1; i < arr.length; i++) {
      maxEnd = max * arr[i];
      minEnd = min * arr[i];
      max = Math.max((Math.max(maxEnd, minEnd)), arr[i]);
      min = Math.min((Math.min(maxEnd, minEnd)), arr[i]);
      res = Math.max(max, res);
    }

    return res;
  }


  /**
   * 数组的partition调整 <p> 给定一个有序数组arr, 调整arr使得这个数组的左半部分没有重复元素且升序，而不用保证右半部分是否有序
   *
   * @param arr 数组 arr
   */
  public static void leftUnique(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }

    int u = 0;
    int i = 1;
    while (i != arr.length) {
      if (arr[i++] != arr[u]) {
        swap(arr, ++u, i - 1);
      }
    }
  }

  public static void swap(int[] arr, int n1, int n2) {
    int temp = arr[n1];
    arr[n1] = arr[n2];
    arr[n2] = temp;
  }

  /**
   * 荷兰国旗问题，三色球问题 <p> 给定一个数组arr, 其中只可能含有0，1，2三个值，实现排序：0全在数组左边，1全在数组中间，2全在数组右边
   *
   * @param arr 数组arr
   */
  public static void partiton(int[] arr) {
    if (arr == null || arr.length == 0) {
      return;
    }

    int left = -1;
    int index = 0;
    int right = arr.length;

    while (index < right) {
      if (arr[index] == 0) {
        swap(arr, index++, ++left);
      } else if (arr[index] == 2) {
        swap(arr, index++, --right);
      } else {
        index++;
      }
    }
  }


  /**
   * 求最短通路值 <p> 用一个整型矩阵matrix表示一个网络，1代表有路，0代表无路，每一个位置只要不越界，都有上下左右四个方向 <p> 求从最左上角到最右下角的最短通路值。 <p>
   * 广度遍历即可 <p> 解法：1）开始时生成map矩阵，map[i][j]的含义是从(0,0)位置走到(i,j)位置最短路径值。然后将左上角位置(0,0)的行坐标 <p>
   * 与列坐标放入队列rQ，和队列cQ。 <p> 2）不断从队列弹出一个位置(r,c),然后看这个位置的上下左右四个位置哪些在matrix上的值是1，这些都是能走的位置。 <p> 3)
   * 将那些能走的位置设置好各自在map中的值，即map[r][c]+1。同时将这些位置加入到rQ和cQ中，用队列完成宽度优先遍历 <p>
   * 4)在步骤三中，如果一个位置之前走过，就不要重复走，这个逻辑可以根据一个位置在map中的值来确定，比如map[i][j]!=0，就可以知道 这个位置之前已经走过。 <p>
   * 5)一直重复步骤2~4。直到遇到右下角位置，说明已经找到终点，返回终点在map中的值即可，如果rQ和cQ已经为空都没有遇到终点位置，说明不存在这样的一条路径，返回0 <p>
   * 每个位置最多走一遍，所以时间复杂度是O(N*M)、额外空间复杂度也是
   *
   * @param m 矩阵m
   * @return 返回最短路径
   */
  public static int minPathValue(int[][] m) {
    if (m == null || m.length == 0 || m[0].length == 0 || m[0][0] != 1
        || m[m.length - 1][m[0].length - 1] != 1) {
      return 0;//非法值
    }

    int col = m[0].length;
    int rol = m.length;
    int res = 0;
    int[][] pathMap = new int[rol][col];
    pathMap[0][0] = 1;
    Queue<Integer> rQ = new LinkedList<>();
    Queue<Integer> cQ = new LinkedList<>();

    rQ.add(0);
    cQ.add(0);
    int r = 0;
    int c = 0;
    while (!rQ.isEmpty()) {
      r = rQ.poll();
      c = cQ.poll();
      if (r == m.length - 1 && c == m[0].length - 1) {
        return pathMap[r][c];
      }

      walkTo(pathMap[r][c], r - 1, c, m, pathMap, rQ, cQ);//up
      walkTo(pathMap[r][c], r + 1, c, m, pathMap, rQ, cQ);//down
      walkTo(pathMap[r][c], r, c - 1, m, pathMap, rQ, cQ);//left
      walkTo(pathMap[r][c], r, c + 1, m, pathMap, rQ, cQ);//right
    }

    return res;
  }

  public static void walkTo(int pre, int toR, int toC, int[][] m, int[][] map, Queue<Integer> rQ,
      Queue<Integer> cQ) {
    if (toR < 0 || toR == m.length || toC < 0 || toC == m[0].length || m[toR][toC] != 1
        || map[toR][toC] != 0) {
      return;
    }

    map[toR][toC] = pre + 1;
    rQ.add(toR);
    cQ.add(toC);
  }

  /**
   * @param arr 数组arr
   * @return 数组中未出现的最小正整数
   */
  public static int missNum(int[] arr) {
    if (arr == null || arr.length < 2) {
      return 0;
    }

    int len = arr.length;
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < len; i++) {
      min = Math.min(min, arr[i]);
      max = Math.max(max, arr[i]);
    }

    if (min == max) {
      return 0;
    }

    boolean[] hasNum = new boolean[len + 1];
    int[] maxs = new int[len + 1];
    int[] mins = new int[len + 1];
    int bid = 0;
    for (int i = 0; i < len; i++) {
      bid = bucket(arr[i], len, min, max);//算出桶号
      mins[bid] = hasNum[bid] ? Math.min(mins[bid], arr[i]) : arr[i];
      maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], arr[i]) : arr[i];
      hasNum[bid] = true;
    }

    int res = 0;
    int lastMax = 0;
    int i = 0;
    while (i <= len) {
      if (hasNum[i++]) {
        lastMax = maxs[i - 1];
        break;
      }
    }

    for (; i <= len; i++) {
      if (hasNum[i]) {
        res = Math.max(res, mins[i] - lastMax);
        lastMax = maxs[i];
      }
    }

    return res;
  }

  //使用long类型是为了防止相乘时溢出
  public static int bucket(long num, long len, long min, long max) {
    return (int) ((num - min) * len / (max - min));
  }

  public int gcd(int m, int n) {
    return n == 0 ? m : gcd(n, m % n);
  }

  /**
   * 部分有序的二分查找
   *
   * @param arr 数组arr
   * @param key 要查找的数
   */
  public static int partitalBinarySearch(int[] arr, int key) {
    if (arr == null || arr.length == 0) {
      return -1;//非法值
    }

    int left = 0;
    int right = arr.length - 1;
    int mid;

    while (left <= right) {
      mid = (left + right) / 2;
      if (arr[mid] == key) {
        return mid;
      } else if (arr[mid] < arr[right]) {
        if (arr[mid] < key && arr[right] >= key) {
          left = mid + 1;
        } else {
          right = mid - 1;
        }
      } else {
        if (arr[mid] > key && arr[left] <= key) {
          right = mid - 1;
        } else {
          left = mid + 1;
        }
      }
    }

    return -1;
  }


  /**
   * @param arr 数组arr
   * @return 返回四个角都为1的矩阵的个数
   */
  public static int getNumberOfMatrix(int[][] arr) {
    return -1;
  }
}
