package 数据结构和算法.算法.跳表;

/**
 * @author like
 * @date 2021-01-22 11:14
 * @contactMe 980650920@qq.com
 * @description
 */
public class SkipList<K, V> {

    public static final int MAX_LEVEL = 32;
    private int size;
    private Comparable<K> comparable;
    private Node<K, V> first;

    public SkipList(Comparable<K> comparable) {
        this.comparable = comparable;
        first = new Node<>();
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
        return null;
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
        return null;
    }

    public int compare(K k1, K k2) {
        return comparable == null
                ? ((Comparable<K>) k1).compareTo(k2)
                : compare(k1, k2);
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V>[] next;
    }
}
