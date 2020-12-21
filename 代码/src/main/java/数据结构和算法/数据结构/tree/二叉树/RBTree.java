package 数据结构和算法.数据结构.tree.二叉树;

import java.util.Comparator;

/**
 * @author like
 * @date 2020-12-21 10:21
 * @contactMe 980650920@qq.com
 * @description 红黑树
 */
public class RBTree<T> extends BinarySearchTree<T> {
    public static final boolean RED = false;
    public static final boolean BLACK = true;

    public RBTree(Comparator<T> comparator) {
        super(comparator);
    }

    public RBTree() {
        this(null);
    }

    /**
     * 添加后恢复成红黑树
     *
     * @param node 节点
     */
    @Override
    protected void addAfter(Node<T> node) {
        super.addAfter(node);
    }

    /**
     * 删除后恢复成红黑树
     *
     * @param node 节点
     */
    @Override
    protected void removeAfter(Node<T> node) {
        super.removeAfter(node);
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
    }
}
