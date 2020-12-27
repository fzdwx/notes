package 数据结构和算法.数据结构.tree.二叉树;

import java.util.Comparator;
import java.util.Stack;

/**
 * @author like
 * @date 2020-12-15 12:07
 * @contactMe 980650920@qq.com
 * @description 二叉搜索树
 */
public class BinarySearchTree<T> extends BinaryTree<T> {

    private Comparator<T> comparator;


    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public BinarySearchTree() {
        this(null);
    }

    public void pre(Visitor<T> visitor) {
        if (root == null) return;
        Node<T> node = this.root;
        Stack<Node<T>> stack = new Stack<>();
        while (true) {
            if (node != null) {
                if (visitor.visit(node)) return;
                if (node.right != null) stack.push(node.right);
                node = node.left;
            } else if (stack.isEmpty()) return;
            else node = stack.pop();
        }
    }

    public void in(Visitor<T> visitor) {
        if (root == null) return;
        Node<T> node = this.root;
        Stack<Node<T>> stack = new Stack<>();
        while (true) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else if (stack.isEmpty()) return;
            else {
                node = stack.pop();
                if (visitor.visit(node)) return;
                node = node.right;
            }

        }
    }

    public void post(Visitor<T> visitor) {
        if (root == null) return;
        Node<T> node = root;
        Node<T> pop = null;
        Stack<Node<T>> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node<T> peek = stack.peek();
            if (peek.isLeaf() || (pop != null && pop.parent == peek)) {
                pop = stack.pop();
                if (visitor.visit(pop)) return;
            } else {
                if (peek.right != null) {
                    stack.push(peek.right);
                }
                if (peek.left != null) {
                    stack.push(peek.left);
                }
            }
        }
    }

    /**
     * 添加后
     * 留给子类去实现，调整树->平衡二叉树
     *
     * @param node 添加的节点
     */
    protected void addAfter(Node<T> node) {
    }

    /**
     * 删除后
     *
     * @param node 节点
     */
    protected void removeAfter(Node<T> node) {
    }

    /**
     * 创建节点的类型
     *
     * @param element 元素
     * @param parent  父
     * @return {@link Node<T>}
     */
    protected Node<T> createNode(T element, Node<T> parent) {
        return new Node<>(element, parent);
    }

    @Override
    public Node<T> getNodeForElement(T element) {
        return getNodeUseIteration(element);
    }

    @Override
    public void add(T element) {
        elementNotNullCheck(element);

        // 1.初始化条件
        Node<T> temp = this.root;                      // 遍历的元素
        Node<T> parent = this.root;                   // 插入元素的父节点
        int compare = 0;                             // 记录插入位置
        Node<T> curr = createNode(element, parent); // 需要插入的元素

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
                    temp.element = element;     // 相等覆盖
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

        addAfter(curr);         // 添加后的操作
    }

    /**
     * 删除
     *
     * @param node 节点
     */
    @Override
    public void remove(Node<T> node) {
        if (node == null) {
            return;
        }

        if (node.hasTwoChildren()) {        // n2
            Node<T> s = predecessor(node);   // 要删除节点的后继节点
            node.element = s.element;     // 删除当前节点(覆盖当前节点所保存的值)
            node = s;
        }

        //n1、n0
        Node<T> removeNext = node.left != null ? node.left : node.right;   // 判断要删除的节点是否有子节点
        if (removeNext != null) {                                         // n1
            removeNext.parent = node.parent;                             // removeNext -> node.parent
            if (node.parent == null) {                                  // 根节点
                root = removeNext;
            } else if (node == node.parent.left) {                    // node.parent.left/right -> removeNext
                node.parent.left = removeNext;
            } else {
                node.parent.right = removeNext;
            }
        } else if (node.parent == null) {  // n0且没有父节点 ->root
            root = null;
        } else {     // n0  直接删除
            if (node.parent.left == node) {
                node.parent.left = null;
            } else if (node.parent.right == node) {
                node.parent.right = null;
            }
        }

        // 恢复平衡
        removeAfter(node);

        size--;
    }

    @Override
    public Object root() {
        return this.root;
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
}
