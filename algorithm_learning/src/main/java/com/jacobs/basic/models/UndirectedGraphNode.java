package com.jacobs.basic.models;

import java.util.ArrayList;

/**
 * Created by lichao on 2017/10/14.
 */
public class UndirectedGraphNode {

  public int label;
  public ArrayList<UndirectedGraphNode> neighbors;

  public UndirectedGraphNode(int x) {
    label = x;
    neighbors = new ArrayList<UndirectedGraphNode>();
  }
}
