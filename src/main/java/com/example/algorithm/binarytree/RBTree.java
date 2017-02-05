package com.example.algorithm.binarytree;

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


}