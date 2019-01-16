package com.jacobs.basic.algorithm.binarytree;

import java.util.Arrays;

/**
 * 有20 个数组，每个数组有500 个元素，并且是有序排列好的，现在在这20*500 个数中找出排名前500 的数
 *
 * @author lichao
 * @date 2018/12/8
 */
public class BinaryHeap {

  public static void main(String[] args) {
    int[][] matrix = generateRandomMatrix(5, 10, 1000);
    printMatrix(matrix);

    System.out.println("===========================");

    printTopK(matrix, 100);
  }

  /**
   * 这个数据结构存的值，二维数组的row --> arrNum， 以及二维数组的col --> index
   *
   * 用来定位每个node被打印之后接下来要往大顶堆里面填充的值
   */
  public static class HeapNode {

    public int value;

    public int arrNum;

    public int index;

    public HeapNode(int value, int arrNum, int index) {

      this.value = value;

      this.arrNum = arrNum;

      this.index = index;

    }
  }


  public static void printTopK(int[][] matrix, int topK) {

    int heapSize = matrix.length;

    HeapNode[] heap = new HeapNode[heapSize];

    //刚开始构建大顶堆
    for (int i = 0; i != heapSize; i++) {

      int index = matrix[i].length - 1;

      heap[i] = new HeapNode(matrix[i][index], i, index);

      heapInsert(heap, i);

    }

    System.out.println("TOP " + topK + " : ");

    for (int i = 0; i != topK; i++) {

      if (heapSize == 0) {

        break;

      }

      //这里第一个顶元素即为我们要求的局部最大，打印出来
      System.out.print(heap[0].value + " ");

      //如果没有到达二维数组heap[0].arrNum行的头部，说明堆的大小还是不能减小
      if (heap[0].index != 0) {
        heap[0].value = matrix[heap[0].arrNum][--heap[0].index];
      } else {
        //否则减小堆的大小，把最顶上已经打印的node与最后一个替换，并减小堆size，这样相当于删除了那个节点
        swap(heap, 0, --heapSize);
      }

      //重新调整堆，使得堆重新变成大顶堆
      heapify(heap, 0, heapSize);
    }

  }


  public static void heapInsert(HeapNode[] heap, int index) {

    while (index != 0) {

      int parent = (index - 1) / 2;

      if (heap[parent].value < heap[index].value) {

        swap(heap, parent, index);

        index = parent;

      } else {

        break;

      }

    }

  }


  public static void heapify(HeapNode[] heap, int index, int heapSize) {

    int left = index * 2 + 1;

    int right = index * 2 + 2;

    int largest = index;

    while (left < heapSize) {

      if (heap[left].value > heap[index].value) {

        largest = left;

      }

      if (right < heapSize && heap[right].value > heap[largest].value) {

        largest = right;

      }

      if (largest != index) {

        swap(heap, largest, index);

      } else {

        break;

      }

      index = largest;

      left = index * 2 + 1;

      right = index * 2 + 2;

    }

  }


  public static void swap(HeapNode[] heap, int index1, int index2) {

    HeapNode tmp = heap[index1];

    heap[index1] = heap[index2];

    heap[index2] = tmp;

  }

  public static int[][] generateRandomMatrix(int maxRow, int maxCol,

      int maxValue) {

    if (maxRow < 0 || maxCol < 0) {
      return null;
    }

    int[][] matrix = new int[(int) (Math.random() * maxRow) + 1][];

    for (int i = 0; i != matrix.length; i++) {

      matrix[i] = new int[(int) (Math.random() * maxCol) + 1];

      for (int j = 0; j != matrix[i].length; j++) {

        matrix[i][j] = (int) (Math.random() * maxValue);

      }

      Arrays.sort(matrix[i]);

    }

    return matrix;

  }


  public static void printMatrix(int[][] matrix) {

    for (int i = 0; i != matrix.length; i++) {

      for (int j = 0; j != matrix[i].length; j++) {

        System.out.print(matrix[i][j] + " ");

      }

      System.out.println();

    }

  }
}
