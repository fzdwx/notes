package 数据结构和算法.数据结构.tree.二叉树;

import java.util.Comparator;

/**
 * @author like
 * @date 2020-12-18 10:22
 * @contactMe 980650920@qq.com
 * @description
 */
public class AVLTree<T> extends BinarySearchTree<T> {
    public AVLTree(Comparator<T> comparator) {
        super(comparator);
    }

    public AVLTree() {
    }

    @Override
    protected void addAfter(Node<T> node) {
        // 1.找到最近的失衡节点
        while ((node = node.parent) != null) {
            if (isBalance((node))) {        // 判断当前节点是否是平衡的
                // 更新高度
                updateHeight(node);
            } else {
                // 恢复平衡
                toRestoreBalance(node);
                break;
            }
        }
    }

    /**
     * 恢复平衡
     *
     * @param grand 祖父节点
     */
    private void toRestoreBalance(Node<T> grand) {
        Node<T> parent = ((AVLNode<T>) grand).tallerChild();
        Node<T> node = ((AVLNode<T>) parent).tallerChild();

        if (parent.isLeftChild()) {        // l
            if (node.isLeftChild()) {     // ll  - 右旋转
                rightRotation(grand);
            } else {                    // lr  左旋转 -> 右旋转
                leftRotation(parent);
                rightRotation(grand);
            }
        } else {                         // l
            if (node.isRightChild()) {  // rr 左旋转
                leftRotation(grand);
            } else {                   // rl 右旋转 -> 左旋转
                rightRotation(parent);
                leftRotation(grand);
            }
        }
    }

    /**
     * 右旋转
     *
     * @param grand 节点
     */
    private void rightRotation(Node<T> grand) {
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
    private void leftRotation(Node<T> grand) {
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
    private void afterRo(Node<T> grand, Node<T> parent, Node<T> root, Node<T> child) {
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

        // 更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    /**
     * 是否平衡
     *
     * @param node 当前节点
     * @return boolean
     */
    private boolean isBalance(Node<T> node) {
        return Math.abs(((AVLNode<T>) node).balanceFactor()) <= 1;  // 调用avlNode中的balanceFactor()方法
    }

    /**
     * 更新高度
     *
     * @param node 节点
     */
    private void updateHeight(Node<T> node) {
        ((AVLNode<T>) node).updateHeight();  // 调用avlNode中的updateHeight()方法
    }

    @Override
    protected Node<T> createNode(T element, Node<T> parent) {
        return new AVLNode<T>(element, parent);
    }

    /**
     * avl node
     *
     * @author pdd20
     * @date 2020/12/18
     */
    private static class AVLNode<T> extends Node<T> {
        int height = 1; // 当前节点的高度

        public AVLNode(T element, Node<T> parent) {
            super(element, parent);
        }

        /**
         * 计算当前节点的平衡因子
         *
         * @return int
         */
        public int balanceFactor() {
            int leftH = left == null ? 0 : ((AVLNode<T>) left).height;
            int rightH = right == null ? 0 : ((AVLNode<T>) right).height;
            return leftH - rightH;
        }

        /**
         * 找到最高的子节点
         *
         * @return {@link AVLNode<T>}
         */
        public Node<T> tallerChild() {
            int leftH = left == null ? 0 : ((AVLNode<T>) left).height;
            int rightH = right == null ? 0 : ((AVLNode<T>) right).height;
            if (rightH > leftH) return right;
            if (rightH < leftH) return left;
            return isLeftChild() ? left : right;
        }

        /**
         * 更新当前节点的高度
         */
        private void updateHeight() {
            int leftH = left == null ? 0 : ((AVLNode<T>) left).height;
            int rightH = right == null ? 0 : ((AVLNode<T>) right).height;
            height = Math.max(leftH, rightH) + 1;
        }

        @Override
        public String toString() {
            return "AVLNode{" +
                    "height=" + height +
                    ", element=" + element +
                    '}';
        }
    }

    @Override
    public Object string(Object node) {
        return ((AVLNode<T>) node).element + "_h:" + (((AVLNode<T>) node).height);
    }
}
