package 算法.图.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author like
 * @date 2021-01-05 11:01
 * @contactMe 980650920@qq.com
 * @description
 */
public abstract class Graph<V, E> {
    protected WeightManager<E> weightManager;

    public Graph() {
    }

    public Graph(WeightManager<E> weightManager) {
        this.weightManager = weightManager;
    }

    public abstract void print();

    public abstract int verticesSize();

    public abstract int edgesSize();

    public abstract void addVertex(V v);

    public abstract void addEdge(V from, V to);

    public abstract void addEdge(V from, V to, E weight);

    public abstract void removeVertex(V v);

    public abstract void removeEdge(V from, V to);

    public abstract void bfs(V root, VertexVisitor<V> visitor);

    public abstract void dfs(V root, VertexVisitor<V> visitor);

    public abstract void dfs2(V root, VertexVisitor<V> visitor);

    /**
     * 最短路径，求权值
     */
    public abstract Map<V, E> shortestPath(V start);

    /**
     * 最短路径线
     *
     * @return
     */
    public abstract Map<V, PathInfo<V, E>> shortestPathLine(V start);

    /**
     * 多源最短路径线floyd
     */
    public abstract Map<V, Map<V, PathInfo<V, E>>> shortestPathLine();

    /**
     * 最小生成树
     */
    public abstract Set<EdgeInfo<V, E>> mst();

    /**
     * 拓扑排序,必须是有向无环图
     */
    public abstract List<V> topologicalSort();

    public interface VertexVisitor<V> {
        boolean visit(V v);
    }

    public interface WeightManager<E> {
        int compare(E w1, E w2);

        E add(E w1, E w2);

        E zero();
    }


    public static class PathInfo<V, E> {
        public E weight;
        private List<EdgeInfo<V, E>> edgeInfos = new LinkedList<>();

        public PathInfo() {
        }

        public PathInfo(E zero) {
            this.weight = zero;
        }

        public E getWeight() {
            return weight;
        }

        public void setWeight(E weight) {
            this.weight = weight;
        }

        public List<EdgeInfo<V, E>> getEdgeInfos() {
            return edgeInfos;
        }

        public boolean addEdgeInfo(EdgeInfo<V, E> edgeInfo) {
            return edgeInfos.add(edgeInfo);
        }

        @Override
        public String toString() {
            return "PathInfo{" +
                    "weight=" + weight +
                    ", edgeInfos=" + edgeInfos +
                    '}';
        }
    }

    public static class EdgeInfo<V, E> {
        V from;
        V to;
        E weight;

        public EdgeInfo(V from, V to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EdgeInfo{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }

}
