package 数据结构和算法.数据结构.map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author like
 * @date 2020-12-23 12:18
 * @contactMe 980650920@qq.com
 * @description 红黑树实现的Map
 */
public class TreeMap<K, V> implements Map<K, V> {
    public static final boolean RED = false;
    public static final boolean BLACK = true;
    private int size;
    private Node<K, V> root;
    private Comparator<K> comparator;

    public TreeMap() {
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new RuntimeException("key 不能为空");
        }

        // 1.初始化条件
        Node<K, V> temp = this.root;                      // 遍历的元素
        Node<K, V> parent = this.root;                   // 插入元素的父节点
        int compare = 0;                             // 记录插入位置
        Node<K, V> curr = createNode(key, value, parent); // 需要插入的元素

        // 2.找到父节点，以及插入位置
        // 2.a.判断根节点是否为空
        if (temp != null) {
            // 2.b.存在根节点，查找当前节点的父节点  parent.left/right = curr
            while (temp != null) {
                compare = compare(key, temp.key);
                parent = temp;
                if (compare > 0) {
                    temp = temp.right;
                } else if (compare < 0) {
                    temp = temp.left;
                } else {
                    V tempValue = temp.value;
                    temp.key = key;         // 相等覆盖
                    temp.value = value;
                    return tempValue;
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

        afterPut(curr);         // 添加后的操作

        return null;
    }

    public V remove(K key) {
        return get(key);
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node == null ? null : node.value;
    }


    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<K, V> poll = queue.poll();
            if (poll.value.equals(value)) {
                return true;
            }
            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }

        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) {
            return;
        }
        traversal(root, visitor);
    }

    private Node<K, V> node(K key) {
        if (key == null || root == null) { return null; }
        Node<K, V> temp = this.root;
        while (temp != null) {
            Integer d = compare(key, temp.key);
            if (d == 0) { return temp; } // 返回节点
            if (d > 0) { temp = temp.right; } else { temp = temp.left; }
        }
        return null;
    }

    private Integer compare(K key, K compare) {
        if (comparator == null) {
            return ((Comparable<K>) key).compareTo(compare);
        }
        return comparator.compare(key, compare);
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;       // 当前节点的左子节点
        Node<K, V> right;     // 当前节点的右子节点
        Node<K, V> parent;   // 当前节点的父节点
        /**
         * 当前节点的颜色(默认为红色) red or black
         */
        boolean color = RED;
        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean hasTwoChildren() {
            return right != null && left != null;
        }

        public boolean isLeaf() {
            return right == null && left == null;
        }

        public Node<K, V> uncle() {
            if (parent == null) {
                return null;
            }
            return parent.sibling();
        }

        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<K, V> grand() {
            if (parent == null) {
                return null;
            }
            return parent.parent == null ? null : parent.parent;
        }
    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        Node<K, V> grand = node.grand();
        Node<K, V> uncle = node.uncle();

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
            afterPut(toRed(grand));
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
     * 旋转之后调整父节点的位置
     *
     * @param grand  大
     * @param parent 父
     * @param root   根
     * @param child  parent的子节点
     */
    private void afterRo(Node<K, V> grand, Node<K, V> parent, Node<K, V> root, Node<K, V> child) {
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

    /**
     * 给节点染色
     *
     * @param node  节点
     * @param color 颜色
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) {return null;}
        node.color = color;
        return node;
    }

    /**
     * 查看节点的颜色
     * 如果节点是null则返回黑色
     *
     * @param node 节点
     * @return boolean
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : ((Node<K, V>) node).color;
    }

    private Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * 左旋转
     *
     * @param grand 节点
     */
    private void leftRotation(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> root = grand.parent;
        Node<K, V> t1 = parent.left;

        // 交换位置 连线
        grand.right = parent.left;
        parent.left = grand;

        afterRo(grand, parent, root, t1);
    }

    private Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        Node<K, V> p = node.left;
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

    private V remove(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        V removeValue = node.value;

        if (node.hasTwoChildren()) {        // n2
            Node<K, V> s = predecessor(node);   // 要删除节点的后继节点
            node.key = s.key;     // 删除当前节点(覆盖当前节点所保存的值)
            node.value = s.value;     // 删除当前节点(覆盖当前节点所保存的值)
            node = s;
        }

        //n1、n0
        Node<K, V> removeNext = node.left != null ? node.left : node.right;   // 判断要删除的节点是否有子节点
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
        return removeValue;
    }

    private void removeAfter(Node<K, V> node) {
        Node<K, V> son = node.left != null ? node.left : node.right;
        Node<K, V> parent = node.parent;


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
        Node<K, V> sib = isLeft ? parent.right : parent.left;

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
     * 右旋转
     *
     * @param grand 节点
     */
    private void rightRotation(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> root = grand.parent;
        Node<K, V> t2 = parent.right;

        grand.left = parent.right;
        parent.right = grand;
        // 更新父节点
        afterRo(grand, parent, root, t2);
    }

    private Node<K, V> toBlack(Node<K, V> node) {
        return color(node, BLACK);
    }

    private Node<K, V> toRed(Node<K, V> node) {
        return color(node, RED);
    }

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null) {
            return;
        }
        traversal(root.left, visitor);
        boolean visit = visitor.visit(node.key, node.value);
        if (visit) {
            return;
        }
        traversal(root.right, visitor);
    }
}
