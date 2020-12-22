package 数据结构和算法.数据结构.tree.二叉树;

import java.util.Comparator;

/**
 * @author like
 * @date 2020-12-21 10:21
 * @contactMe 980650920@qq.com
 * @description 红黑树
 */
public class RedBlackTree<T> extends BinaryBalanceSearchTree<T> {
    public static final boolean RED = false;
    public static final boolean BLACK = true;

    public RedBlackTree(Comparator<T> comparator) {
        super(comparator);
    }

    public RedBlackTree() {
        this(null);
    }


    @Override
    protected Node<T> createNode(T element, Node<T> parent) {
        return new RBNode<>(element, parent);
    }

    @Override
    public Object string(Object node) {
        return node;
    }

    /**
     * 添加后恢复成红黑树
     *
     * @param node 节点
     */
    @Override
    protected void addAfter(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> grand = node.grand();
        Node<T> uncle = node.uncle();

        // 1.添加的是根节点 或 上溢到达了根节点
        if (parent == null) {
            toBlack(node);
            return;
        }

        // 2.如果父节点是黑色，直接返回
        if (isBlack(parent)) { return; }

        // 3.判断uncle是不是red
        if (isRed(uncle)) {   // 红色，上溢
            // 3.1、parent和uncle染成黑色
            toBlack(parent);
            toBlack(uncle);
            // 3.2、grand向上合并(当做是新添加的节点)
            addAfter(toRed(grand));
        } else {         // 不是红色
            // 旋转
            if (parent.isLeftChild()) {
                if (node.isLeftChild()) {  // LL
                    toBlack(parent);
                    toRed(grand);
                    rightRotation(grand);
                } else {  // LR
                    toBlack(node);
                    toRed(grand);
                    leftRotation(parent);
                    rightRotation(grand);
                }
            } else {
                if (node.isRightChild()) {  //RR
                    toBlack(parent);
                    toRed(grand);
                    leftRotation(grand);
                } else { // RL
                    toBlack(node);
                    rightRotation(parent);
                    toRed(grand);
                    leftRotation(grand);
                }
            }
        }
    }

    /**
     * 删除后恢复成红黑树
     *
     * @param node 节点
     */
    @Override
    protected void removeAfter(Node<T> node) {
        Node<T> son = node.left != null ? node.left : node.right;
        Node<T> parent = node.parent;


        // 1.要删除的是RED->直接删除
        if (isRed(node)) { return; }

        // 2. 要删除的是n1,子节点是BLACK(n2这种情况不存在)
        if (isRed(son)) {
            toBlack(son); // 将子节点染黑
            return;
        }

        // 3.删除是n0,BLACK节点(红黑树默认补null为BLACK)
        if (parent == null) {return;}   // root是null->直接返回
        boolean isLeft = parent.left == null || node.isLeftChild();
        Node<T> sib = isLeft ? parent.right : parent.left;

        if (isLeft) {   // L
            if (isRed(sib)) {  // 把红色兄弟换成黑色兄弟
                toBlack(sib);
                toRed(parent);
                leftRotation(parent);
                // 更换兄弟
                sib = parent.right;
            }
            // 兄弟节点是黑色
            if (isBlack(sib.right) && isBlack(sib.left)) {
                // 兄弟都是黑色子节点
                boolean color = isBlack(parent);
                toRed(sib);
                toBlack(parent);
                if (color) { // 如果父亲本来就是黑色，就会产生下溢
                    removeAfter(parent);
                }
            } else {// 兄弟至少有一个RED子节点
                if (isBlack(sib.right)) { // 左边是黑色 兄弟进行旋转
                    rightRotation(sib);
                    sib = parent.right;
                }
                color(sib, colorOf(parent));
                toBlack(sib.right);
                toBlack(parent);
                leftRotation(parent);
            }
        } else {  // R
            if (isRed(sib)) {  // 把红色兄弟换成黑色兄弟
                toBlack(sib);
                toRed(parent);
                rightRotation(parent);
                // 更换兄弟
                sib = parent.left;
            }
            // 兄弟节点是黑色
            if (isBlack(sib.left) && isBlack(sib.right)) {
                // 兄弟都是黑色子节点
                boolean color = isBlack(parent);
                toRed(sib);
                toBlack(parent);
                if (color) { // 如果父亲本来就是黑色，就会产生下溢
                    removeAfter(parent);
                }
            } else {// 兄弟至少有一个RED子节点
                if (isBlack(sib.left)) { // 左边是黑色 兄弟进行旋转
                    leftRotation(sib);
                    sib = parent.left;
                }
                color(sib, colorOf(parent));
                toBlack(sib.left);
                toBlack(parent);
                rightRotation(parent);
            }
        }
    }

    /**
     * 给节点染色
     *
     * @param node  节点
     * @param color 颜色
     */
    private Node<T> color(Node<T> node, boolean color) {
        if (node == null) {return null;}
        ((RBNode<T>) node).color = color;
        return node;
    }

    private Node<T> toRed(Node<T> node) {
        return color(node, RED);
    }

    private Node<T> toBlack(Node<T> node) {
        return color(node, BLACK);
    }

    /**
     * 查看节点的颜色
     * 如果节点是null则返回黑色
     *
     * @param node 节点
     * @return boolean
     */
    private boolean colorOf(Node<T> node) {
        return node == null ? BLACK : ((RBNode<T>) node).color;
    }

    private boolean isRed(Node<T> node) {
        return colorOf(node) == RED;
    }

    private boolean isBlack(Node<T> node) {
        return colorOf(node) == BLACK;
    }


    /**
     * 红黑树的节点
     *
     * @param <T>
     */
    private static class RBNode<T> extends Node<T> {
        /**
         * 当前节点的颜色(默认为红色) red or black
         */
        boolean color = RED;

        public RBNode(T element, Node<T> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String s = "";
            if (color == RED) {
                s = "R_";
            }
            return s + element.toString();
        }
    }
}
