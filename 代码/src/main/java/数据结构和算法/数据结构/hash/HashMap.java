package 数据结构和算法.数据结构.hash;

import 数据结构和算法.数据结构.map.Map;
import 数据结构和算法.数据结构.tree.printer.BinaryTreeInfo;
import 数据结构和算法.数据结构.tree.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @author like
 * @date 2020-12-24 12:02
 * @contactMe 980650920@qq.com
 * @description 哈希Map
 */
public class HashMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private static final int DEFAULT_CAPACITY = 1 << 4; // 最好是2^n次方 16 = 1<<4
    private int size;
    private Node<K, V>[] table;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int capacity) {
        if (capacity < DEFAULT_CAPACITY) {
            capacity = DEFAULT_CAPACITY;
        }
        table = new Node[capacity];
    }

    public void print() {
        if (size == 0) {return;}
        for (Node<K, V> node : table) {
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object root() {
                    return node;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>) node).left;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>) node).right;
                }

                @Override
                public Object string(Object node) {
                    return ((Node<K, V>) node);
                }
            });
        }

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
        if (size == 0) { return; }
        for (Node<K, V> node : table) {
            node = null;
        }
        size = 0;
    }

    @Override
    public V put(K k1, V value) {
        int index = index(k1);
        // 1.取出index位置的红黑树节点
        Node<K, V> root = table[index];
        if (root == null) {  // hash冲突
            // 表示这个索引是第一次添加元素
            root = new Node<>(k1, value, null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }
        // 1.初始化条件
        Node<K, V> parent = root;                            // 插入元素的父节点
        Node<K, V> node = root;                               // 遍历的元素
        int compare = 0;                                    // 记录插入位置
        int h1 = k1 == null ? 0 : k1.hashCode();
        Node<K, V> res = null;
        boolean isSearch = false; // 是否搜索过
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            // 1.比较哈希
            if (h1 > h2) {
                //                    root = root.right;
                compare = 1;
            } else if (h1 < h2) {
                //                    root = root.left;
                compare = -1;
            } else if (Objects.equals(k1, k2)) {
                compare = 0;
                // 2.具备可比较性
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (compare = ((Comparable) k1).compareTo(k2)) != 0) {

            } else if (isSearch) {   // 已经扫描过了
                compare = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else { // 3.扫描
                if (node.left != null && (res = node(node.left, k1)) != null
                        || root.right != null && (res = node(node.right, k1)) != null) {
                    node = res;
                    compare = 0;
                } else {
                    isSearch = true;
                    compare = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }
            // 4.处理compare
            if (compare > 0) {
                node = node.right;
            } else if (compare < 0) {
                node = node.left;
            } else {
                V tempValue = node.value;
                node.key = k1;         // 相等覆盖
                node.value = value;
                return tempValue;
            }
        } while (node != null);

        // 3.添加节点
        Node<K, V> curr = createNode(k1, value, parent);  // 需要插入的元素
        curr.parent = parent;
        if (compare > 0) {
            parent.right = curr;
        } else {
            parent.left = curr;
        }
        size++;

        afterPut(curr);         // 添加后的操作

        return null;
    }


    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (Node<K, V> node : table) {
            if (node == null) {
                continue;
            }
            queue.offer(node);
            while (!queue.isEmpty()) {
                Node<K, V> poll = queue.poll();
                if (Objects.equals(value, node.value)) {
                    return true;
                }
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) {
            return;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (Node<K, V> node : table) {
            if (node == null) {
                continue;
            }
            queue.offer(node);
            while (!queue.isEmpty()) {
                Node<K, V> poll = queue.poll();
                visitor.visit(poll.key, poll.value);
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
        }
    }

    private static class Node<K, V> {
        /**
         * 当前节点的颜色(默认为红色) red or black
         */
        boolean color = RED;
        private int hash;
        private K key;
        private V value;
        private Node<K, V> left;       // 当前节点的左子节点
        private Node<K, V> right;     // 当前节点的右子节点
        private Node<K, V> parent;   // 当前节点的父节点

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hash = key == null ? 0 : key.hashCode();
        }

        @Override
        public String toString() {
            return "k_" + key + ",v_" + value;
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

    private void afterRemove(Node<K, V> node) {
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
                    afterRemove(parent);
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
                    afterRemove(parent);
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
     * 在罗
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
            table[index(grand)] = parent;
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
        return node == null ? BLACK : node.color;
    }

    private Integer compare(K k1, K k2, int h1, int h2) {
        int res = h1 - h2;
        if (res != 0) {
            return res;
        }
        if (Objects.equals(k1, k2)) {
            return 0;
        }
        if (k1 != null && k2 != null
                && k1.getClass() == k2.getClass()
                && k1 instanceof Comparable) {
            return ((Comparable) k1).compareTo(k2);

        }
        // 同一种类型不具备可比较性
        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }

    private Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    /**
     * 根据key生成对应的索引（在桶数组中的位置）
     */
    private int index(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }

    private int index(Node<K, V> node) {
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
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

    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return root == null ? null : node(root, key);
    }

    /**
     * 节点
     *
     * @param k1   需要查找的key
     * @param root key所在的树的根节点
     * @return {@link Node<K, V>}
     */
    private Node<K, V> node(Node<K, V> root, K k1) {
        int h1 = k1 == null ? 0 : k1.hashCode();
        Node<K, V> res = null;
        int cmp = 0;
        while (root != null) {
            K k2 = root.key;
            int h2 = root.hash;
            // 1、比较哈希
            if (h1 > h2) {
                root = root.right;
            } else if (h1 < h2) {
                root = root.left;
            } else if (Objects.equals(k1, k2)) {
                return root;
                // 2、具备可比较性
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k2 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                root = cmp > 0 ? root.right : root.left;
                // 3、哈希相等，不具备可比较性
            } else if (root.right != null && (res = node(root.right, k1)) != null) {
                return res;
            } else {
                root = root.left;
            }
            //            } else if (root.left != null && (res = node(root.left, k1)) != null) {
            //                return res;
            //            } else {
            //                return null;
            //            }
        }
        return null;
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
            Node<K, V> s = successor(node);   // 要删除节点的后继节点
            node.key = s.key;     // 删除当前节点(覆盖当前节点所保存的值)
            node.value = s.value;     // 删除当前节点(覆盖当前节点所保存的值)
            node.hash = s.hash;
            node = s;
        }

        //n1、n0
        Node<K, V> removeNext = node.left != null ? node.left : node.right;   // 判断要删除的节点是否有子节点
        if (removeNext != null) {                                         // n1
            removeNext.parent = node.parent;                             // removeNext -> node.parent
            if (node.parent == null) {                                  // 根节点
                table[index(node.key)] = removeNext;
            } else if (node == node.parent.left) {                    // node.parent.left/right -> removeNext
                node.parent.left = removeNext;
            } else {
                node.parent.right = removeNext;
            }
        } else if (node.parent == null) {  // n0且没有父节点 ->root
            table[index(node.key)] = null;
        } else {     // n0  直接删除
            if (node.parent.left == node) {
                node.parent.left = null;
            } else if (node.parent.right == node) {
                node.parent.right = null;
            }
        }

        // 恢复平衡
        afterRemove(node);

        size--;
        return removeValue;
    }

    /**
     * 右旋转
     *
     * @param grand 节点
     */
    private void rightRotation(Node<K, V> grand) {
        int hash;
        Node<K, V> parent = grand.left;
        Node<K, V> root = grand.parent;
        Node<K, V> t2 = parent.right;

        grand.left = parent.right;
        parent.right = grand;
        // 更新父节点
        afterRo(grand, parent, root, t2);
    }

    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        Node<K, V> p = node.right;
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

    private Node<K, V> toBlack(Node<K, V> node) {
        return color(node, BLACK);
    }

    private Node<K, V> toRed(Node<K, V> node) {
        return color(node, RED);
    }
}
