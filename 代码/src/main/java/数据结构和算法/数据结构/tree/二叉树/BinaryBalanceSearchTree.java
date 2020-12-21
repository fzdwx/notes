package 数据结构和算法.数据结构.tree.二叉树;

import java.util.Comparator;

/**
 * @author like
 * @date 2020-12-21 12:08
 * @contactMe 980650920@qq.com
 * @description 平衡二叉搜索树
 */
public class BinaryBalanceSearchTree<T> extends  BinarySearchTree<T> {
    public BinaryBalanceSearchTree(Comparator<T> comparator) {
        super(comparator);
    }

    public BinaryBalanceSearchTree() {
        this(null);
    }

    /**
     * 统一的旋转
     *
     * @param grand 大
     * @param d     d
     * @param a     a
     * @param b     b
     * @param c     c
     * @param e     e
     * @param f     f
     * @param g     g
     */
    protected void rotation(Node<T> grand,
                          Node<T> a, Node<T> b, Node<T> c,
                          Node<T> d,
                          Node<T> e, Node<T> f, Node<T> g) {
        d.parent = grand.parent;
        if (grand.isRightChild()) {
            grand.parent.right = d;
        } else if (grand.isLeftChild()) {
            grand.parent.left = d;
        } else {
            root = d;
        }

        doIt(a, b, c);
        doIt(e, f, g);
        doIt(b, d, f);
    }

    protected void doIt(Node<T> a, Node<T> b, Node<T> c) {
        b.left = a;
        b.right = c;
        if (a != null) {
            a.parent = b;
        }
        if (c != null) {
            c.parent = b;
        }
    }

    /**
     * 右旋转
     *
     * @param grand 节点
     */
    protected void rightRotation(Node<T> grand) {
        Node<T> parent = grand.left;
        Node<T> root = grand.parent;
        Node<T> t2 = parent.right;

        grand.left = parent.right;
        parent.right = grand;
        // 更新父节点
        afterRo(grand, parent, root, t2);
    }

    /**
     * 左旋转
     *
     * @param grand 节点
     */
    protected void leftRotation(Node<T> grand) {
        Node<T> parent = grand.right;
        Node<T> root = grand.parent;
        Node<T> t1 = parent.left;

        // 交换位置 连线
        grand.right = parent.left;
        parent.left = grand;

        afterRo(grand, parent, root, t1);
    }

    /**
     * 旋转之后调整父节点的位置
     *
     * @param grand  大
     * @param parent 父
     * @param root   根
     * @param child  parent的子节点
     */
    protected void afterRo(Node<T> grand, Node<T> parent, Node<T> root, Node<T> child) {
        // 更新root节点的子节点
        if (grand.isLeftChild()) {
            root.left = parent;
        } else if (grand.isRightChild()) {
            root.right = parent;
        } else {   // root
            this.root = parent;
        }

        // 更新父节点
        parent.parent = root;
        if (child != null) {
            child.parent = grand;
        }
        grand.parent = parent;
    }

}
