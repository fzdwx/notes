package 数据结构和算法.数据结构.tree.二叉树;

import 数据结构和算法.数据结构.list.List;
import 数据结构和算法.数据结构.list.array.ArrayList;
import 数据结构和算法.数据结构.tree.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author like
 * @date 2020-12-17 16:08
 * @contactMe 980650920@qq.com
 * @description
 */
public abstract class BinaryTree<T> implements BinaryTreeInfo {

    protected int size;
    protected Node<T> root;

    /**
     * 获得元素的节点
     *
     * @param element 元素
     * @return {@link Node<T>}
     */
    public abstract Node<T> getNodeForElement(T element);

    public abstract void add(T element);

    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean contains(T element) {
        return getNodeForElement(element) != null;
    }

    /**
     * 删除
     *
     * @param element 元素
     */
    public void remove(T element) {
        // 1.删除叶子节点(n0)  - 直接删除
        // 2.删除n1  - 用子节点代替原节点的位置 node.left/right.parent = node.parent
        // 3.删除n2  先用前驱或后继节点覆盖原节点，然后删除对应的前驱或后继
        remove(getNodeForElement(element));
    }

    /**
     * 删除
     *
     * @param node 节点
     */
    public abstract void remove(Node<T> node);

    /**
     * 高度
     *
     * @param type 类型  1 ->递归  2->迭代
     * @return int
     */
    public int height(int type) {
        if (type == 1) {
            return height(root);
        } else if (type == 2) {
            return heightIteration();
        }
        return 0;
    }

    /**
     * 判断是不是完全二叉树
     *
     * @return boolean
     */
    public boolean isComplete() {
        if (root == null) {
            return false;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<T> poll = queue.poll();  // 当前层级元素出队
            if (leaf && !poll.isLeaf()) {
                return false;
            }
            if (poll.left != null) { // 入队
                queue.offer(poll.left);
            } else if (poll.right != null) { // 没有左子节点，但有右子节点 -> 不是
                return false;
            }
            if (poll.right != null) { // 入队
                queue.offer(poll.right);
            } else {  // 右子节点为空，则往后的节点都必须是叶子节点
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 得到节点使用递归
     *
     * @param element 元素
     * @return {@link Node<T>}
     */
    protected Node<T> getNodeUseRecursive(T element) {
        if (element == null || root == null) {
            return null;
        }

        ArrayList<Node<T>> list = new ArrayList<>();
        inorderTraversal(new Visitor<T>() {
            @Override
            public boolean visit(Node<T> node) {
                if (node.element == element) {
                    list.add(node);
                    return true;
                }
                return false;
            }
        });

        // 添加判断
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * 前驱
     *
     * @param node 节点
     * @return {@link Node<T>}
     */
    public Node<T> predecessor(Node<T> node) {
        if (node == null) {
            return null;
        }
        Node<T> p = node.left;
        // 前驱节点在左子树中,left.right.right.right  ...
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }
        p = node;
        // 前驱节点在父节点的左父节点
        while (p.parent != null && p == p.parent.left) { // 父节点==null，当前节点是父节点的右节点
            p = p.parent;
        }
        return p.parent;
    }

    /**
     * 后继
     *
     * @param node 节点
     * @return {@link Node<T>}
     */
    public Node<T> successor(Node<T> node) {
        if (node == null) {
            return null;
        }
        Node<T> p = node.right;
        // 后继节点在右子树中,right.left.left.left  ...
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }
        p = node;
        // 后继节点在父节点的右父节点
        while (p.parent != null && p == p.parent.right) { // 父节点==null，当前节点是父节点的左节点
            p = p.parent;
        }
        return p.parent;
    }

    /**
     * 前序遍历
     *
     * @return {@link List <T>}
     */
    public void preorderTraversal(BinarySearchTree.Visitor<T> visitor) {
        if (visitor == null) {
            return;
        }
        preorderTraversal(root, visitor);
    }


    /**
     * 中序遍历
     *
     * @return {@link List<T>}
     */
    public void inorderTraversal(BinarySearchTree.Visitor<T> visitor) {
        if (visitor == null) {
            return;
        }
        inorderTraversal(root, visitor);
    }

    /**
     * 后序遍历
     *
     * @return {@link List<T>}
     */
    public void postorderTraversal(BinarySearchTree.Visitor<T> visitor) {
        if (visitor == null) {
            return;
        }
        postorderTraversal(root, visitor);
    }

    /**
     * 层序遍历
     *
     * @return {@link List<T>}
     */
    public void levelOrderTraversal(BinarySearchTree.Visitor<T> visitor) {
        if (root == null || visitor == null || visitor.continueTo) {
            return;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<T> poll = queue.poll();
            visitor.continueTo = visitor.visit(poll); // 如果为true 停止遍历
            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
    }

    /**
     * 前序遍历
     *
     * @param root    根
     * @param visitor 游客
     */
    protected void preorderTraversal(Node<T> root, Visitor<T> visitor) {
        if (root == null || visitor.continueTo) {
            return;
        }
        // root 节点
        visitor.continueTo = visitor.visit(root);
        // 前序遍历 左子树
        preorderTraversal(root.left, visitor);
        // 前序遍历 右子树
        preorderTraversal(root.right, visitor);

    }

    /**
     * 中序遍历
     *
     * @param root    根
     * @param visitor 游客
     */
    protected void inorderTraversal(Node<T> root, Visitor<T> visitor) {
        if (root == null || visitor.continueTo) {
            return;
        }
        inorderTraversal(root.left, visitor);
        if (visitor.continueTo) {
            return;
        }
        visitor.continueTo = visitor.visit(root);
        inorderTraversal(root.right, visitor);
    }

    /**
     * 后序遍历
     *
     * @param root    根
     * @param visitor 游客
     */
    protected void postorderTraversal(Node<T> root, Visitor<T> visitor) {
        if (root == null || visitor.continueTo) {
            return;
        }
        postorderTraversal(root.left, visitor);
        postorderTraversal(root.right, visitor);
        if (visitor.continueTo) {
            return;
        }
        visitor.continueTo = visitor.visit(root);
    }


    /**
     * 高度
     * 递归
     *
     * @param root 根
     * @return int
     */
    protected int height(Node<T> root) {
        if (root == null) {
            return 0;
        }
        return Math.max(height(root.left), height(root.right)) + 1;
    }

    /**
     * height 迭代
     *
     * @return int
     */
    protected int heightIteration() {
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        int h = 0;  // 记录高度
        int levelSize = 1; // 记录当前层级元素剩余个数
        while (!queue.isEmpty()) {
            Node<T> poll = queue.poll();  // 当前层级元素出队
            levelSize--;
            if (poll.left != null) {
                queue.offer(poll.left);   // 把当前层级元素入队
            }
            if (poll.right != null) {
                queue.offer(poll.right);  // 把当前层级元素入队
            }
            if (levelSize == 0) {        // 如果当前层级元素出队完毕
                levelSize = queue.size();
                h++;
            }
        }
        return h;
    }


    /**
     * 元素not null检查
     *
     * @param element 元素
     */
    protected void elementNotNullCheck(T element) {
        if (element == null) {
            throw new IllegalArgumentException("element 不能为空");
        }
    }

    public static class Node<T> {
        T element;
        Node<T> left;       // 当前节点的左子节点
        Node<T> right;     // 当前节点的右子节点
        Node<T> parent;   // 当前节点的父节点


        public Node(T element, Node<T> parent) {
            this.element = element;
            this.parent = parent;
        }


        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    '}';
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public boolean hasTwoChildren() {
            return right != null && left != null;
        }

        public boolean isLeaf() {
            return right == null && left == null;
        }

        public Node<T> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }

        public Node<T> uncle() {
            if (parent == null) {
                return null;
            }
            return parent.sibling();
        }

        public Node<T> grand() {
            if (parent == null) {
                return null;
            }
            return parent.parent==null?null:parent.parent;
        }
    }


    public static abstract class Visitor<T> {
        /**
         * 访问
         * 返回false 继续遍历
         * 返回true 停止遍历
         *
         * @param node 节点
         * @return boolean
         */
        public abstract boolean visit(Node<T> node);

        public boolean continueTo = false;
    }

}
