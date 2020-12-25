# 哈希表

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



# 实现hash表



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