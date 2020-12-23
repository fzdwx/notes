package 数据结构和算法.数据结构.map;

/**
 * @author like
 * @date 2020-12-23 12:14
 * @contactMe 980650920@qq.com
 * @description
 */
public interface Map<K,V> {
    int size();

    boolean isEmpty();

    void clear();

    V put(K key, V value);

    V get(K key);

    boolean containsKey(K key);
    boolean containsValue(V value);

    void traversal(Visitor<K, V> visitor);

    interface Visitor<K, V> {
        boolean visit(K key,V value);
    }

}
