package 数据结构和算法.数据结构.tree;

import 数据结构和算法.数据结构.list.List;
import 数据结构和算法.数据结构.list.array.ArrayList;
import 数据结构和算法.数据结构.tree.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author like
 * @date 2020-12-15 12:07
 * @contactMe 980650920@qq.com
 * @description 二叉搜索树
 */
public class BinarySearchTree<T> implements BinaryTreeInfo {

    private int size;
    public Node<T> root;
    private Comparator<T> comparator;


    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public BinarySearchTree() {
        this(null);
    }


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
     * @return {@link List<T>}
     */
    public void preorderTraversal(Visitor<T> visitor) {
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
    public void inorderTraversal(Visitor<T> visitor) {
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
    public void postorderTraversal(Visitor<T> visitor) {
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
    public void levelOrderTraversal(Visitor<T> visitor) {
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


    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    public void clear() {

    }

    public void add(T element) {
        elementNotNullCheck(element);

        // 1.初始化条件
        Node<T> temp = this.root;                      // 遍历的元素
        Node<T> parent = this.root;                   // 插入元素的父节点
        int compare = 0;                             // 记录插入位置
        Node<T> curr = new Node<>(element, parent); // 需要插入的元素

        // 2.找到父节点，以及插入位置
        // 2.a.判断根节点是否为空
        if (temp != null) {
            // 2.b.存在根节点，查找当前节点的父节点  parent.left/right = curr
            while (temp != null) {
                compare = compare(element, temp.element);
                parent = temp;
                if (compare > 0) {
                    temp = temp.right;
                } else if (compare < 0) {
                    temp = temp.left;
                } else {
                    temp.element = element;
                    return;
                }
            }
        } else {
            root = curr;
        }

        // 3.添加节点
        curr.parent = parent;
        if (compare > 0) {
            parent.right = curr;
        } else if (compare < 0) {
            parent.left = curr;
        }
        size++;

    }

    public void remove(T element) {
        // 1.删除叶子节点(n0)  - 直接删除
        // 2.删除n1  - 用子节点代替原节点的位置 node.left/right.parent = node.parent
        // 3.删除n2  先用前驱或后继节点覆盖原节点，然后删除对应的前驱或后继
    }

    public void contains(T element) {

    }

    public int height(int num) {
        if (num == 1) {
            return height(root);
        } else if (num == 2) {
            return height2();
        }
        return 0;
    }


    /**
     * 判断是否是完全二叉树
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

            if (leaf && !poll.isLeaf()) {  // 如果没有叶子节点
                return false;
            }
            if (poll.hasChildren()) { //如果当前节点有子节点
                queue.offer(poll.left);
                queue.offer(poll.right);
            } else if (poll.left == null && poll.right != null) {  // 如果没有左子节点但是有右子节点->不是
                return false;
            } else {
                leaf = true;
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
            }

        }
        return true;
    }

    public boolean isComplete2() {
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
     * 获得元素的节点
     *
     * @param element 元素
     * @return {@link Node<T>}
     */
    public Node<T> getNodeForElement(T element) {
        return getNodeUseRecursive(element);
    }

    /**
     * 得到节点使用递归
     *
     * @param element 元素
     * @return {@link Node<T>}
     */
    private Node<T> getNodeUseRecursive(T element) {
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
     * 比较
     *
     * @param element 需要添加的元素
     * @param compare 比较的对象
     * @return {@link Integer}
     * 返回0 表示相等         覆盖
     * 大于0 表示element大    右边
     * 小于0 表示compare大    左边
     */
    private Integer compare(T element, T compare) {
        if (comparator == null) {
            return ((Comparable<T>) element).compareTo(compare);
        }
        return comparator.compare(element, compare);
    }

    private void preorderTraversal(Node<T> root, Visitor<T> visitor) {
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
     * 有条不紊地进行遍历
     *
     * @param root    根
     * @param visitor 游客
     */
    private void inorderTraversal(Node<T> root, Visitor<T> visitor) {
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
     * 后根次序遍历
     *
     * @param root    根
     * @param visitor 游客
     */
    private void postorderTraversal(Node<T> root, Visitor<T> visitor) {
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
    private int height(Node<T> root) {
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
    private int height2() {
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
    private void elementNotNullCheck(T element) {
        if (element == null) {
            throw new IllegalArgumentException("element 不能为空");
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<T>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<T>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<T>) node).element;
    }
}
