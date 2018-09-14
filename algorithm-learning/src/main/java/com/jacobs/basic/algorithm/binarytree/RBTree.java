package com.jacobs.basic.algorithm.binarytree;

/**
 * Created by lichao on 2017/2/5.
 */
public class RBTree<T extends Comparable<T>> {

  private RBTNode<T> root;    // 根结点

  private static final boolean RED = false;
  private static final boolean BLACK = true;


  /*
* 对红黑树的节点(x)进行左旋转
*
* 左旋示意图(对节点x进行左旋)：
*      px                              px
*     /                               /
*    x                               y
*   /  \      --(左旋)-.           / \                #
*  lx   y                          x  ry
*     /   \                       /  \
*    ly   ry                     lx  ly
*
*
*/
  public void leftRotate(RBTNode<T> x) {
    // 设置x的右孩子赋值给y
    RBTNode<T> y = x.right;

    // 将 “y的左孩子” 设为 “x的右孩子”；
    // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
    if (y.left != null) {
      y.left.parent = x;
    }

    // 将 “x的父亲” 设为 “y的父亲”
    y.parent = x.parent;

    if (x.parent == null) {
      this.root = y;  //若x的父节点为空，则把y设为根节点
    } else {
      if (x.parent.left == x) {
        x.parent.left = y; // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
      } else {
        x.parent.right = y;// 如果 x是它父节点的右孩子，则将y设为“x的父节点的右孩子”
      }
    }

    // 将 “x” 设为 “y的左孩子”
    y.left = x;
    // 将 “x的父节点” 设为 “y”
    x.parent = y;
  }


  /*
 * 对红黑树的节点(y)进行右旋转
 *
 * 右旋示意图(对节点y进行左旋)：
 *            py                               py
 *           /                                /
 *          y                                x
 *         /  \      --(右旋)-.            /  \                     #
 *        x   ry                           lx   y
 *       / \                                   / \                   #
 *      lx  rx                                rx  ry
 *
 */
  public void rightRotate(RBTNode<T> y) {
    //设置x为当前节点的左孩子
    RBTNode<T> x = y.left;

    if (x.right != null) {
      y.left = x.right;
    }

    x.parent = y.parent;
    if (y.parent == null) {
      this.root = x;
    } else {
      if (y.parent.left == y) {
        y.parent.left = x; // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
      } else {
        y.parent.right = x; // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
      }
    }

    x.right = y;
    y.parent = x;
  }


  public void insertFixUp(RBTNode<T> node) {
    RBTNode<T> parent, gparent;

    while (((parent = node.parent) != null) && parent.color) {
      gparent = parent.parent;

      if (parent == gparent.left) {
        RBTNode<T> uncleNode = gparent.right;
        //左孩子
        if (gparent.right != null && uncleNode.color) {
          //case 1: 当前节点父节点和叔叔节点均为红色，策略:父节点和叔叔节点
          //变成黑色，祖父节点变成红色，并将当前节点指向祖父节点
          parent.color = false;
          uncleNode.color = false;
          gparent.color = true;
          node = gparent;

          continue;
        }

        if (parent.right == node && !uncleNode.color) {
          //case 2:当前节点是父节点的右孩子，并且叔叔节点是黑色
          //策略：将当前节点指向父节点，并以父节点为基础左旋
          leftRotate(parent);
          node = parent;
        }

        if (parent.left == node && !uncleNode.color) {
          //case 3: 当前节点是父节点的左孩子，并且叔叔节点是黑色
          //策略:将祖父节点变为红色，父节点变成黑色，并以祖父节点为基础右旋
          gparent.color = true;
          parent.color = false;
          rightRotate(gparent);
        }
      } else {
        //若当前父节点是祖父节点的右孩子（对称的逻辑）

      }

    }
  }


}