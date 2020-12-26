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
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;  // 默认装填因子   size / table.length
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
        resize();

        int index = index(k1);
        // 1.取出index位置的红黑树节点
        Node<K, V> root = table[index];
        if (root == null) {  // hash冲突
            // 表示这个索引是第一次添加元素
            root = createNode(k1, value, null);
            table[index] = root;
            size++;
            fixAfterPut(root);
            return null;
        }
        // 1.初始化条件
        Node<K, V> parent = root;                            // 插入元素的父节点
        Node<K, V> node = root;                               // 遍历的元素
        int compare = 0;                                    // 记录插入位置
        int h1 = hash(k1);
        Node<K, V> res = null;
        boolean isSearch = false; // 是否搜索过
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            // 1.比较哈希
            if (h1 > h2) {
                compare = 1;
            } else if (h1 < h2) {
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

        fixAfterPut(curr);         // 添加后的操作

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

    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    protected V remove(Node<K, V> node) {
        if (node == null) return null;
        Node<K, V> willNode = node;
        size--;

        V oldValue = node.value;

        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<K, V> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            // 删除后继节点
            node = s;

        }

        // 删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                table[index] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理
            fixAfterRemove(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            table[index] = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }

            // 删除节点之后的处理
            fixAfterRemove(node);
        }
        // 交给子类处理
        afterRemove(willNode, node);

        return oldValue;
    }

    protected void replaceNode() {

    }
    protected void afterRemove(Node<K,V> willNode, Node<K, V> node) { }

    protected static class Node<K, V> {
        protected K key;
        protected V value;
        protected Node<K, V> left;       // 当前节点的左子节点
        protected Node<K, V> right;     // 当前节点的右子节点
        protected Node<K, V> parent;   // 当前节点的父节点
        /**
         * 当前节点的颜色(默认为红色) red or black
         */
        boolean color = RED;
        private int hash;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            int hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
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

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 让parent称为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // grand是root节点
            table[index(grand)] = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
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

    private void fixAfterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            toBlack(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) {
            return;
        }

        // 叔父节点
        Node<K, V> uncle = parent.sibling();
        // 祖父节点
        Node<K, V> grand = toRed(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
            toBlack(parent);
            toBlack(uncle);
            // 把祖父节点当做是新添加的节点
            fixAfterPut(grand);
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                toBlack(parent);
            } else { // LR
                toBlack(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                toBlack(node);
                rotateRight(parent);
            } else { // RR
                toBlack(parent);
            }
            rotateLeft(grand);
        }
    }

    private void fixAfterRemove(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            toRed(node);
            return;
        }

        Node<K, V> parent = node.parent;
        if (parent == null) {
            return;
        }

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                toBlack(sibling);
                toRed(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }
            if (sibling == null) {
                return;
            }
            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                toBlack(parent);
                toRed(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                toBlack(sibling.right);
                toBlack(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                toBlack(sibling);
                toRed(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }
            if (sibling == null) {
                return;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                toBlack(parent);
                toRed(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                toBlack(sibling.left);
                toBlack(parent);
                rotateRight(parent);
            }
        }
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

    /**
     * 根据key生成对应的索引（在桶数组中的位置）
     */
    private int index(K key) {
        return hash(key) & (table.length - 1);
    }

    private int index(Node<K, V> node) {
        return node.hash & (table.length - 1);
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * 扩容的时候移动节点
     *
     * @param newNode 节点
     */
    private void move(Node<K, V> newNode) {
        // 重置
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;

        int index = index(newNode);
        // 取出index位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            fixAfterPut(root);
            return;
        }

        // 添加新的节点到红黑树上面
        Node<K, V> parent;
        Node<K, V> node = root;
        int cmp;
        K k1 = newNode.key;
        int h1 = newNode.hash;
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        // 新添加节点之后的处理
        fixAfterPut(newNode);
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
        int h1 = hash(k1);
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

    /**
     * 扩容
     */
    private void resize() {
        // 1.判断装填因子 -> 装填因子小于 < 0.75
        if (((float) (size / table.length)) <= DEFAULT_LOAD_FACTOR) {return;}

        // 2.进行扩容 -> 2倍
        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length << 1];

        Queue<Node<K, V>> queue = new LinkedList<>();  // 遍历每个节点
        for (Node<K, V> kvNode : oldTable) {
            if (kvNode != null) {
                queue.offer(kvNode);
                while (!queue.isEmpty()) {
                    Node<K, V> node = queue.poll();
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }

                    // 挪动代码得放到最后面
                    move(node);
                }
            }
        }
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
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
        // 后继节点在父节点的右父节点
        while (node.parent != null && node == node.parent.right) { // 父节点==null，当前节点是父节点的左节点
            node = node.parent;
        }
        return node.parent;
    }

    private Node<K, V> toBlack(Node<K, V> node) {
        return color(node, BLACK);
    }

    private Node<K, V> toRed(Node<K, V> node) {
        return color(node, RED);
    }
}
