package 数据结构和算法.算法.图;

/**
 * @author like
 * @date 2021-01-05 11:01
 * @contactMe 980650920@qq.com
 * @description
 */
public interface Graph<V, E> {
    void print();

    int verticesSize();

    int edgesSize();

    void addVertex(V v);

    void addEdge(V from, V to);

    void addEdge(V from, V to, E weight);

    void removeVertex(V v);

    void removeEdge(V from, V to);
}
