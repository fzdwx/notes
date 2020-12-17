package 数据结构和算法.数据结构.tree.二叉树;

import java.util.Comparator;

/**
 * @author like
 * @date 2020-12-15 12:07
 * @contactMe 980650920@qq.com
 * @description 二叉搜索树
 */
public class BinarySearchTree<T> extends BinaryTree<T>  {

    private int size;
    public Node<T> root;
    private Comparator<T> comparator;


    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public BinarySearchTree() {
        this(null);
    }

    @Override
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

    @Override
    public Node<T> getNodeForElement(T element) {
        return getNodeUseIteration(element);
    }

    /**
     * 得到节点使用迭代
     *
     * @param element 元素
     * @return {@link Node<T>}
     */
    private Node<T> getNodeUseIteration(T element) {
        if (element == null || root == null) {
            return null;
        }

        Node<T> temp = this.root;
        while (temp != null) {
            Integer d = compare(element, temp.element);
            if (d == 0) {
                return temp;   // 返回节点
            }
            if (d > 0) {
                temp = temp.right;
            } else {
                temp = temp.left;
            }
        }
        return null;
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
}
