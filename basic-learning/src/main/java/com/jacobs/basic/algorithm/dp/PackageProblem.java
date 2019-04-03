package com.jacobs.basic.algorithm.dp;

import com.google.common.collect.Lists;

import java.util.List;

import lombok.Data;

/**
 * Created by lichao on 2017/2/20.
 */
public class PackageProblem {
  public static void main(String[] args) {
    List<Item> itemList = Lists.newArrayList(new Item(6, 2), new Item(3, 2), new Item(5, 6),
        new Item(4, 5), new Item(6, 4));
    getMaxValue(itemList, 10);
  }

  public static List<Item> getMaxValue(List<Item> itemses, int destination) {
    int[][] valueMatrix = new int[itemses.size()][destination + 1];
    for (int i = 0; i < itemses.size(); i++) {
      valueMatrix[i][0] = 0;
    }

    for (int j = 0; j < valueMatrix[0].length; j++) {
      if (itemses.get(0)
          .getWeight() > j) {
        valueMatrix[0][j] = 0;
      } else {
        valueMatrix[0][j] = itemses.get(0)
            .getValue();
      }
    }

    for (int i = 1; i < valueMatrix[0].length; i++) {
      for (int j = 1; j < itemses.size(); j++) {
        if (itemses.get(j)
            .getWeight() > i) {
          valueMatrix[j][i] = valueMatrix[j - 1][i];
        } else {
          valueMatrix[j][i] = Math.max((valueMatrix[j - 1][i] + itemses.get(j)
              .getValue()), valueMatrix[j - 1][i - itemses.get(j)
              .getWeight()]);
        }
      }
    }

    //回溯
    int currentSize = destination;
    List<Item> result = Lists.newArrayList();
    for (int i = itemses.size() - 1; i >= 0; i--) {
      Item item = itemses.get(i);
      if (currentSize == 0) {
        break;
      } else if (i == 0) {
        if (currentSize >= item.getWeight()) {
          result.add(item);
          System.out.println("weight: " + item.getWeight() + " , val: " + item.getValue());
        }
      } else if (valueMatrix[i][currentSize] - valueMatrix[i - 1][currentSize - item.getWeight()] ==
          item.value) {
        result.add(item);
        System.out.println("weight: " + item.getWeight() + " , val: " + item.getValue());
        currentSize = currentSize - item.getWeight();
      }
    }

    return result;
  }


  @Data
  public static class Item {
    int value;
    int weight;

    public Item(int value, int weight) {
      this.value = value;
      this.weight = weight;
    }
  }
}

