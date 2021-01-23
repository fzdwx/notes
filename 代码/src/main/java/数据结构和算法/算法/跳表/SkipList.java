package 数据结构和算法.算法.跳表;

/**
 * @author like
 * @date 2021-01-22 11:14
 * @contactMe 980650920@qq.com
 * @description
 */
public class SkipList<K, V> {

    public static final int MAX_LEVEL = 32;
    public static final double P = 0.25;
    private int size;
    private Comparable<K> comparable;
    private Node<K, V> first;
    /** 有效层数 **/
    private int level;

    public SkipList(Comparable<K> comparable) {
        this.comparable = comparable;
        first = new Node<>(null, null, MAX_LEVEL);
        first.next = new Node[MAX_LEVEL];
    }

    public SkipList() {
        this(null);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V put(K key, V value) {
        Node<K, V> node = this.first;
        Node<K, V>[] prev = new Node[level];
        for (int i = level - 1; i > 0; i--) {
            int cmp = -1;
            while (node.next[i] != null && (cmp = compare(key, node.next[1].key)) > 0) {
                node = node.next[1];
            }
            // node.next[1].key >= key
            if (cmp == 0) return node.next[i].value;  // 节点存在
            prev[i] = node;
        }
        // 添加新节点
        int newLevel = randomLevel();
        Node<K, V> newNode = new Node<>(key, value, newLevel);
        for (int i = 0; i < newLevel; i++) {
            if (i >= level) {
                first.next[i] = newNode;
            } else {
                newNode.next[i] = prev[i].next[i];
                prev[i].next[i] = newNode;
            }
        }

        size++;
        level = Math.max(level, newLevel);
        return value;
    }

    public V get(K key) {
        Node<K, V> node = this.first;
        for (int i = level - 1; i > 0; i--) {
            int cmp = -1;
            while (node.next[i] != null && (cmp = compare(key, node.next[1].key)) > 0) {
                node = node.next[1];
            }
            // node.next[1].key >= key
            if (cmp == 0) return node.next[i].value;
        }
        return null;
    }

    public V remove(K key) {
        Node<K, V> node = this.first;
        Node<K, V>[] prev = new Node[level];
        boolean exist = false;
        for (int i = level - 1; i > 0; i--) {
            int cmp = -1;
            while (node.next[i] != null && (cmp = compare(key, node.next[1].key)) > 0) {
                node = node.next[1];
            }
            prev[i] = node;
            if (cmp == 0) {
                exist = true;
            }
        }
        if (!exist) { return null;}
        Node<K, V> remove = node.next[0];
        for (int i = 0; i < remove.next.length; i++) {
            prev[i].next[i] = remove.next[i];
        }
        int newLevel = this.level;
        while (--newLevel >= 0 && first.next[newLevel] == null) {
            level=newLevel;
        }
        size--;
        return remove.value;
    }

    public int compare(K k1, K k2) {
        return comparable == null
                ? ((Comparable<K>) k1).compareTo(k2)
                : compare(k1, k2);
    }

    private int randomLevel() {
        int level = 1;
        for (int i = 0; i < MAX_LEVEL; i++) {
            if (Math.random() < P) {
                level++;
            }
        }
        return level;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V>[] next;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            next = new Node[level];
        }
    }
}
