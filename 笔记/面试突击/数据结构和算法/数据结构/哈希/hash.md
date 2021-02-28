# 哈希表简介

Hash Table(哈希表)

![image-20201223141934882](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201223141935.png)



# 哈希冲突

![image-20201223142215054](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201223142215.png)



# jdk中解决哈希冲突的方式

![image-20201223162059013](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201223162059.png)





# 哈希函数

1. 先生成`key`的`哈希值`(整数)
2. 在让这个哈希值跟数组的大小进行相关计算，生成一个索引值

![image-20201223162856889](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201223162857.png)



# 实现hashMap



## node()

在哈希表根据key查找，返回key所在的节点



```java
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
                && k2 instanceof Comparable) {
            int cmp = ((Comparable) k1).compareTo(k2);
            if (cmp > 0) {
                root = root.right;
            } else if (cmp < 0) {
                root = root.left;
            } else {
                return root;
            }
            // 3、哈希相等，不具备可比较性
        } else if (root.right != null && (res = node(root.right, k1)) != null) {
            return res;
        } else if (root.left != null && (res = node(root.left, k1)) != null) {
            return res;
        } else {
            return null;
        }
    }
    return null;
}
```



## index()

根据key计算出对应的索引

```java
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
```





## hash节点类

```java
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
```





## hashMap

```java
public class HashMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private static final int DEFAULT_CAPACITY = 16; // 最好是2^n次方 16 = 1<<4
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
}
```





## put()

```java
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

    afterPut(curr);         // 添加后的操作

    return null;
}
```





## remove()

```java
private V remove(Node<K, V> node) {
    if (node == null) return null;

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
        afterRemove(replacement);
    } else if (node.parent == null) { // node是叶子节点并且是根节点
        table[index] = null;
    } else { // node是叶子节点，但不是根节点
        if (node == node.parent.left) {
            node.parent.left = null;
        } else { // node == node.parent.right
            node.parent.right = null;
        }

        // 删除节点之后的处理
        afterRemove(node);
    }

    return oldValue;
}
```





# 扩容

通过扩容因子 

## 装填因子

也叫负载因子：节点总数量 / 哈希表数组长度 

```java
public static final float DEFAULT_LOAD_FACTOR = 0.75f;  // 默认装填因子   size / table.length
```





## 实现

```java
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
```

```java
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
        afterPut(root);
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
    afterPut(newNode);
}
```





# 扩容优化

数组的长度尽量为**素数**(只能被1和自身整除)，这样可以大大减少哈希冲突

![image-20201226130810302](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201226130810.png)

下表素数有一下特点：

- 每个素数略小于前一个素数的2倍
- 每个素数尽可能接近2的幂

![image-20201226130604818](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201226130611.png)





# LinkedHashMap

可以按照添加顺序来遍历



```java
public class LinkedHashMap<K, V> extends HashMap<K, V> {
    private LinedNode<K, V> first;
    private LinedNode<K, V> last;

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) { return; }
        LinedNode<K, V> node = this.first;
        while (node != null) {
            if (visitor.visit(node.key, node.value)) {return;}
            node = node.next;
        }

    }

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinedNode<K, V> node = new LinedNode<>(key, value, parent);
        if (first == null) {
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        return node;
    }

    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> node) {
        LinedNode<K, V> n1 = (LinedNode<K, V>) willNode;
        LinedNode<K, V> n2 = (LinedNode<K, V>) node;

        if (n2 != n1) {
            // 交換 prev
            LinedNode<K, V> temp = n1.prev;
            n1.prev = n2.prev;
            n2.prev = temp;
            if (n1.prev == null) {
                first = n1;
            } else {
                n1.prev.next = n1;
            }
            if (n2.prev == null) {
                first = n2;
            } else {
                n2.prev.next = n2;
            }
            // 交換 prev
            temp = n1.next;
            n1.next = n2.next;
            n2.next = temp;
            if (n1.next == null) {
                last = n1;
            } else {
                n1.next.prev = n1;
            }
            if (n2.next == null) {
                last = n2;
            } else {
                n2.next.prev = n2;
            }
        }

        // 刪除
        LinedNode<K, V> prev = n1.prev;
        LinedNode<K, V> next = n1.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }


    public static class LinedNode<K, V> extends Node<K, V> {
        private LinedNode<K, V> prev;
        private LinedNode<K, V> next;

        public LinedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }
}
```