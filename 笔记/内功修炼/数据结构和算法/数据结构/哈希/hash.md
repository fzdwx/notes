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
/**
 * 根据Key生成索引
 */
private int index(K key) {
    if (key == null) { return 0; }
    int hash = key.hashCode();
    return index(hash);
}

private int index(int hash) {
    return hash ^ (hash >>> 16) & (table.length - 1);
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
public V put(K key, V value) {
    int index = index(key);
    // 1.取出index位置的红黑树节点
    Node<K, V> root = table[index];
    if (root != null) {  // hash冲突
        // 1.初始化条件
        Node<K, V> temp = root;                               // 遍历的元素
        Node<K, V> parent = root;                            // 插入元素的父节点
        int compare = 0;                                    // 记录插入位置
        Node<K, V> curr = createNode(key, value, parent);  // 需要插入的元素
        int h1 = curr.hash;
        K k1 = key;
        Node<K, V> res = null;
        do {
            parent = temp;
            K k2 = temp.key;
            int h2 = temp.hash;
            // 1.比较哈希
            if (h1 > h2) {
                root = root.right;
                compare = 1;
            } else if (h1 < h2) {
                root = root.left;
                compare = -1;
            } else if (Objects.equals(k1, k2)) {
                compare = 0;
                // 2.具备可比较性
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k2 instanceof Comparable) {
                compare = ((Comparable) k1).compareTo(k2);
            } else { // 3.扫描
                if (root.left != null && (res = node(root.left, k1)) != null) {
                    root = res;
                    compare = 0;
                } else if (root.right != null && (res = node(root.right, k1)) != null) {
                    root = res;
                    compare = 0;
                } else {
                    compare = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }
            // 4.处理compare
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
        } while (temp != null);

        // 3.添加节点
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
     // 表示这个索引是第一次添加元素
    root = new Node<>(key, value, null);
    table[index] = root;
    size++;
    afterPut(root);
    return null;
}
```





## remove()

```java
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
            table[index(node.hash)] = removeNext;
        } else if (node == node.parent.left) {                    // node.parent.left/right -> removeNext
            node.parent.left = removeNext;
        } else {
            node.parent.right = removeNext;
        }
    } else if (node.parent == null) {  // n0且没有父节点 ->root
        table[index(node.hash)] = null;
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
```