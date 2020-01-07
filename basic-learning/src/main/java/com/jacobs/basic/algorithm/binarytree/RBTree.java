package com.jacobs.basic.algorithm.binarytree;

/**
 * Created by lichao on 2017/2/5.
 * referenced from https://www.cnblogs.com/skywang12345/p/3624343.html
 *  红黑树特性
 * (1) 每个节点或者是黑色，或者是红色。
 * (2) 根节点是黑色。
 * (3) 每个叶子节点是黑色。 [注意：这里叶子节点，是指为空的叶子节点！]
 * (4) 如果一个节点是红色的，则它的子节点必须是黑色的。
 * (5) 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。
 *
 */
public class RBTree<T extends Comparable<T>> {

    private RBTNode<T> root;    // 根结点
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private RBTNode<T> parentOf(RBTNode<T> node) {
        return node != null ? node.parent : null;
    }

    private boolean colorOf(RBTNode<T> node) {
        return node != null ? node.color : BLACK;
    }

    private boolean isRed(RBTNode<T> node) {
        return ((node != null) && (node.color == RED)) ? true : false;
    }

    private boolean isBlack(RBTNode<T> node) {
        return !isRed(node);
    }

    private void setBlack(RBTNode<T> node) {
        if (node != null)
            node.color = BLACK;
    }

    private void setRed(RBTNode<T> node) {
        if (node != null)
            node.color = RED;
    }

    private void setParent(RBTNode<T> node, RBTNode<T> parent) {
        if (node != null)
            node.parent = parent;
    }

    private void setColor(RBTNode<T> node, boolean color) {
        if (node != null)
            node.color = color;
    }


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

    /**
     * insert a node, need auto adjust to rb tree
     * @param node
     */
    public void insert(RBTNode<T> node) {
        int cmp;
        RBTNode<T> y = null;
        RBTNode<T> x = this.root;

        // find where to insert node
        while (x != null) {
            y = x;
            cmp = node.key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = y;
        if (y != null) {
            cmp = node.key.compareTo(y.key);
            if (cmp < 0) {
                y.left = node;
            } else {
                y.right = node;
            }
        } else {
            this.root = node;
        }
        // 将节点设置成红色
        node.color = RED;
        node.left = null;
        node.right = null;
        // 修正
        insertFixUp(node);
    }

    /**
     * 修正函数，三种case
     * @param node
     */
    public void insertFixUp(RBTNode<T> node) {
        RBTNode<T> parent, grandParent;

        // 当前节点父节点存在，且颜色为红色
        while (((parent = node.parent) != null) && parent.color == RED) {
            grandParent = parent.parent;

            if (parent == grandParent.left) {
                RBTNode<T> uncleNode = grandParent.right;
                //左孩子
                if (grandParent.right != null && isRed(uncleNode)) {
                    //case 1: 当前节点父节点和叔叔节点均为红色
                    // 策略:父节点和叔叔节点变成黑色，祖父节点变成红色，并将当前节点指向祖父节点
                    setBlack(uncleNode);
                    setBlack(parent);
                    setRed(parent);
                    node = grandParent;
                    continue;
                }

                if (parent.right == node && isBlack(uncleNode)) {
                    //case 2:当前节点是父节点的右孩子，并且叔叔节点是黑色
                    //策略：将当前节点指向父节点，并以父节点为基础左旋
                    RBTNode<T> tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                if (parent.left == node && !uncleNode.color) {
                    //case 3: 当前节点是父节点的左孩子，并且叔叔节点是黑色
                    //策略:将祖父节点变为红色，父节点变成黑色，并以祖父节点为基础右旋
                    setRed(grandParent);
                    setBlack(parent);
                    rightRotate(grandParent);
                }
            } else {
                //若当前父节点是祖父节点的右孩子（对称的逻辑）
                RBTNode<T> uncleNode = grandParent.left;
                if (uncleNode != null && isRed(uncleNode)) {
                    setBlack(uncleNode);
                    setBlack(parent);
                    setRed(grandParent);
                    node = grandParent;
                    continue;
                }
                //case2
                if (parent.left == node && isBlack(uncleNode)) {
                    RBTNode<T> tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }
                // case3
                if (parent.right == node && isBlack(uncleNode)) {
                    setBlack(parent);
                    setRed(grandParent);
                    leftRotate(grandParent);
                }
            }
        }

        setBlack(this.root);
    }

    /**
     * 删除操作
     * 第一步：将红黑树当作一颗二叉查找树，将节点删除。
     * 这和"删除常规二叉查找树中删除节点的方法是一样的"。分3种情况：
     * ① 被删除节点没有儿子，即为叶节点。那么，直接将该节点删除就OK了。
     * ② 被删除节点只有一个儿子。那么，直接删除该节点，并用该节点的唯一子节点顶替它的位置。
     * ③ 被删除节点有两个儿子。那么，先找出它的后继节点；然后把“它的后继节点的内容”复制给“该节点的内容”；之后，删除“它的后继节点”。
     *    在这里，后继节点相当于替身，在将后继节点的内容复制给"被删除节点"之后，再将后继节点删除。
     *    这样就巧妙的将问题转换为"删除后继节点"的情况了，下面就考虑后继节点。 在"被删除节点"有两个非空子节点的情况下，它的后继节点不可能是双子非空。
     *    既然"的后继节点"不可能双子都非空，就意味着"该节点的后继节点"要么没有儿子，要么只有一个儿子。若没有儿子，则按"情况① "进行处理；
     *    若只有一个儿子，则按"情况② "进行处理。
     *
     * 第二步：通过"旋转和重新着色"等一系列来修正该树，使之重新成为一棵红黑树。
     *         因为"第一步"中删除节点之后，可能会违背红黑树的特性。所以需要通过"旋转和重新着色"来修正该树，使之重新成为一棵红黑树。
     * @param node
     */
    public void remove(RBTNode<T> node) {
        RBTNode<T> child, parent;
        boolean color;

        // case3
        if (node.left != null && node.right != null) {
            // 被删节点的后继节点。(称为"取代节点")
            // 用它来取代"被删节点"的位置，然后再将"被删节点"去掉。
            RBTNode<T> replace = node;

            replace = replace.right;
            while (replace.left != null) {
                replace = replace.left;
            }

            // "node节点"不是根节点(只有根节点不存在父节点)
            if (parentOf(node) != null) {
                // 更新待删除父节点指向
                if (parentOf(node).left == node)
                    parentOf(node).left = replace;
                else
                    parentOf(node).right = replace;
            } else {
                // "node节点"是根节点，更新根节点。
                this.root = replace;
            }

            // child是"取代节点"的右孩子，也是需要"调整的节点"。
            // "取代节点"肯定不存在左孩子！因为它是一个后继节点。
            child = replace.right;
            parent = parentOf(replace);
            // 保存"取代节点"的颜色
            color = colorOf(replace);

            // "被删除节点"是"它的后继节点的父节点"
            if (parent == node) {
                parent = replace;
            } else {
                // child不为空
                if (child != null)
                    setParent(child, parent);
                parent.left = child;

                replace.right = node.right;
                setParent(node.right, replace);
            }

            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;

            if (color == BLACK)
                removeFixUp(child, parent);

            node = null;
            return;
        }

        // case1 和 case2 统一处理
        if (node.left != null) {
            child = node.left;
        } else {
            child = node.right;
        }

        parent = node.parent;
        // 保存"取代节点"的颜色
        color = node.color;
        if (child != null)
            child.parent = parent;

        // "node节点"不是根节点
        if (parent != null) {
            if (parent.left == node)
                parent.left = child;
            else
                parent.right = child;
        } else {
            this.root = child;
        }

        if (color == BLACK)
            removeFixUp(child, parent);
        node = null;
    }

    /*
     * 红黑树删除修正函数
     *
     * 在从红黑树中删除插入节点之后(红黑树失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     *
     */
    private void removeFixUp(RBTNode<T> node, RBTNode<T> parent) {
        RBTNode<T> other;

        while ((node == null || isBlack(node)) && (node != this.root)) {
            if (parent.left == node) {
                other = parent.right;
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                if ((other.left == null || isBlack(other.left)) &&
                        (other.right == null || isBlack(other.right))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    if (other.right == null || isBlack(other.right)) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }
                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.root;
                    break;
                }
            } else {
                other = parent.left;
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }

                if ((other.left == null || isBlack(other.left)) &&
                        (other.right == null || isBlack(other.right))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.left == null || isBlack(other.left)) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.root;
                    break;
                }
            }
        }

        if (node != null)
            setBlack(node);
    }
}