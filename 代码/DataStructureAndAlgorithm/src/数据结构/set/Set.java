package 数据结构.set;

/**
 * @author like
 * @date 2020-12-23 10:20
 * @contactMe 980650920@qq.com
 * @description
 */
public interface Set<T> {
    int size();

    boolean isEmpty();

    void clear();

    boolean contains(T element);

    void add(T element);

    void remove(T element);

    void traversal(Visitor<T> visitor);

    public static interface Visitor<T> {
        boolean visit(T element);
    }
}
