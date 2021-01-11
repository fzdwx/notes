package 数据结构和算法.算法.图.graph;


import 数据结构和算法.算法.图.UnionFind;

import java.util.*;

/**
 * @author like
 * @date 2021-01-05 11:09
 * @contactMe 980650920@qq.com
 * @description
 */
public class ListGraph<V, E> extends Graph<V, E> {
    private Map<V, Vertex<V, E>> vertices = new HashMap<>();
    private Set<Edge<V, E>> edges = new HashSet<>();
    private Map<Vertex<V, E>, Integer> ins = new HashMap<>();
    private Comparator<Edge<V, E>> comparator = (e1, e2) -> weightManager.compare(e1.weight, e2.weight);

    public ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
    }

    public ListGraph() {
    }

    @Override
    public void print() {
        vertices.forEach((key, value) -> {
            System.out.println(key);
            System.out.println("==={out}===");
            System.out.println(value.fromEdges);
            System.out.println("==={in}===");
            System.out.println(value.toEdges);
            System.out.println("=================");
        });
        edges.forEach(System.out::println);
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public void addVertex(V v) {
        if (vertices.containsKey(v)) return;
        vertices.put(v, new Vertex<>(v));
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, null);
    }

    @Override
    public void addEdge(V from, V to, E weight) {
        // 1.保证from，to两个顶点都存在
        Vertex<V, E> fV = vertices.get(from);
        Vertex<V, E> tV = vertices.get(to);
        if (fV == null) {
            fV = new Vertex<>(from);
            vertices.put(from, fV);
        }
        if (tV == null) {
            tV = new Vertex<>(to);
            vertices.put(to, tV);
        }
        // 2.添加边
        Edge<V, E> e = new Edge<>(fV, tV, weight);
        if (fV.fromEdges.remove(e)) {   // 如果这个边存在就删除
            tV.toEdges.remove(e);
            edges.remove(e);
        }
        fV.fromEdges.add(e);
        tV.toEdges.add(e);
        edges.add(e);
    }

    @Override
    public void removeVertex(V v) {
        Vertex<V, E> vertex = vertices.remove(v);
        if (vertex == null) return;  // 不存在
        for (Iterator<Edge<V, E>> iterator = vertex.fromEdges.iterator(); iterator.hasNext(); ) {
            Edge<V, E> edge = iterator.next();
            edge.to.toEdges.remove(edge);
            // 将当前遍历到的元素edge从集合vertex.outEdges中删掉
            iterator.remove();
            edges.remove(edge);
        }

        for (Iterator<Edge<V, E>> iterator = vertex.toEdges.iterator(); iterator.hasNext(); ) {
            Edge<V, E> edge = iterator.next();
            edge.from.fromEdges.remove(edge);
            // 将当前遍历到的元素edge从集合vertex.inEdges中删掉
            iterator.remove();
            edges.remove(edge);
        }

    }

    @Override
    public void removeEdge(V from, V to) {
        // 1.确定这两个定点存在
        Vertex<V, E> fV = vertices.get(from);
        Vertex<V, E> tV = vertices.get(to);
        if (fV == null) return;
        if (tV == null) return;
        // 2.如果这2个顶点有边连接就删除
        Edge<V, E> e = new Edge<>(fV, tV, null);
        if (fV.fromEdges.remove(e)) {
            tV.toEdges.remove(e);
            edges.remove(e);
        }
    }

    @Override
    public void bfs(V root, VertexVisitor<V> visitor) {
        Set<Vertex<V, E>> visit = new HashSet<>();

        Vertex<V, E> v = vertices.get(root);
        if (v == null) return;
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        queue.offer(v);
        visit.add(v); // 把起始节点加入队列
        while (!queue.isEmpty()) {
            Vertex<V, E> poll = queue.poll();
            if (visitor.visit(poll.value)) return;  // 訪問
            for (Edge<V, E> fE : poll.fromEdges) {
                if (visit.contains(fE.to)) continue; // 判断是否遍历过
                queue.offer(fE.to); // 入队以当前顶点为起点的边的终点
                visit.add(fE.to);  // 标记遍历过了
            }
        }
    }

    @Override
    public void dfs(V root, VertexVisitor<V> visitor) {
        Vertex<V, E> begin = vertices.get(root);
        if (begin == null) return;

        Set<Vertex<V, E>> visit = new HashSet<>();

        dfs(begin, visit, visitor);
    }

    @Override
    public void dfs2(V root, VertexVisitor<V> visitor) {
        Vertex<V, E> begin = vertices.get(root);
        if (begin == null) return;

        Set<Vertex<V, E>> visit = new HashSet<>();
        Stack<Vertex<V, E>> stack = new Stack<>();

        stack.push(begin);
        if (visitor.visit(begin.value)) return;  // 訪問
        visit.add(begin); // 標記已經訪問過了
        while (!stack.isEmpty()) {
            Vertex<V, E> v = stack.pop();
            for (Edge<V, E> edge : v.fromEdges) {
                if (visit.contains(edge.to)) continue;
                stack.push(edge.from);
                stack.push(edge.to);

                visit.add(edge.to);
                if (visitor.visit(edge.to.value)) return; // 訪問
            }
        }
    }

    @Override
    public Map<V, E> shortestPath(V start) {
        Vertex<V, E> vertex = vertices.get(start);
        if (vertex == null) return null;
        Map<Vertex<V, E>, E> paths = new HashMap<>();
        Map<V, E> selectedPaths = new HashMap<>();
        // 初始化
        for (Edge<V, E> edge : vertex.fromEdges) {
            paths.put(edge.to, edge.weight);
        }
        while (!paths.isEmpty()) {
            // 1.获取权重最小的点
            Map.Entry<Vertex<V, E>, E> min = getShortestPath(paths);
            selectedPaths.put(min.getKey().value, min.getValue());
            paths.remove(min.getKey());
            // 2.进行松弛操作->更新兩個頂點之間的最短路徑
            for (Edge<V, E> edge : min.getKey().fromEdges) {
                // 如果已经包含了就跳过
                if (selectedPaths.containsKey(edge.to.value)) continue;
                // a.新的可选的最短路径：vertex到edge.from的最短路径+edge.weight
                E nw = weightManager.add(min.getValue(), edge.weight);
                // b.以前的最短路径
                E ow = paths.get(edge.to);
                // c.覆盖
                if (ow == null || weightManager.compare(nw, ow) < 0) {
                    paths.put(edge.to, nw);
                }
            }
        }
        return selectedPaths;
    }

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        //        return prim();
        return kruskal();
    }

    @Override
    public List<V> topologicalSort() {
        List<V> list = new ArrayList<>();
        Queue<Vertex<V, E>> queue = new LinkedList<>();

        // 1.将所有度为0的入队
        vertices.forEach((v, vertex) -> {
            int size = vertex.toEdges.size();
            if (size == 0) {
                queue.offer(vertex);
            } else {
                ins.put(vertex, size); // 記錄度
            }
        });

        while (!queue.isEmpty()) {
            Vertex<V, E> poll = queue.poll();
            list.add(poll.value);  // 访问
            for (Edge<V, E> edge : poll.fromEdges) {
                int in = ins.get(edge.to) - 1;
                if (in == 0) {
                    queue.offer(edge.to);
                } else {
                    ins.put(edge.to, in);
                }
            }
        }
        return list;
    }

    private void dfs(Vertex<V, E> begin, Set<Vertex<V, E>> visit, VertexVisitor<V> visitor) {
        if (visitor.visit(begin.value)) return;  // 訪問
        visit.add(begin);
        for (Edge<V, E> edge : begin.fromEdges) {
            if (visit.contains(edge.to)) continue;
            dfs(edge.to, visit, visitor);
        }
    }

    /**
     * 得到最短路径，最小权重
     */
    private Map.Entry<Vertex<V,E>, E> getShortestPath(Map<Vertex<V, E>, E> paths) {
        Iterator<Map.Entry<Vertex<V, E>, E>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V, E>, E> min = it.next();
        while (it.hasNext()) {
            Map.Entry<Vertex<V, E>, E> next = it.next();
            if (weightManager.compare(next.getValue(), min.getValue()) < 0) {
                min = next;
            }
        }
        return min;
    }

    /**
     * 最小生成树算法
     */
    private Set<EdgeInfo<V, E>> kruskal() {
        Iterator<Vertex<V, E>> iterator = vertices.values().iterator();
        if (!iterator.hasNext()) return null;

        Vertex<V, E> vertex = iterator.next();
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();   // 最小生成树的边
        PriorityQueue<Edge<V, E>> queue = new PriorityQueue<>(comparator);  // 建堆
        UnionFind<Vertex<V, E>> uf = new UnionFind<>();
        vertices.forEach((v, vx) -> {
            uf.makeSet(vx);
        });
        queue.addAll(vertex.fromEdges);
        while (!queue.isEmpty()) {
            Edge<V, E> e = queue.remove();
            if (uf.isSame(e.from, e.to)) continue;  // 如果加入e，是否构成环
            edgeInfos.add(e.toEdgeInfo());
            uf.union(e.from, e.to);
        }
        return edgeInfos;
    }

    /**
     * 最小生成树算法-prim
     */
    private Set<EdgeInfo<V, E>> prim() {
        Iterator<Vertex<V, E>> iterator = vertices.values().iterator();

        if (!iterator.hasNext()) return null;
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();   // 最小生成树的边
        Set<Vertex<V, E>> verticesAdd = new HashSet<>(); // 标记已经添加过了
        Vertex<V, E> vertex = iterator.next();

        //        PriorityQueue<Edge<V, E>> queue = new PriorityQueue<>(vertex.fromEdges);  // 建堆
        PriorityQueue<Edge<V, E>> queue = new PriorityQueue<>(comparator);  // 建堆
        queue.addAll(vertex.fromEdges);
        int edgeCount = vertices.size() - 1; // 最小生成树的边的数量
        while (!queue.isEmpty() && edgeInfos.size() < edgeCount) {
            Edge<V, E> e = queue.remove();
            if (verticesAdd.contains(e.to)) continue;

            edgeInfos.add(e.toEdgeInfo());  // 最小生成树的边
            verticesAdd.add(e.to);  // 标记已经添加过了
            queue.addAll(e.to.fromEdges);  // 继续遍历
        }
        return edgeInfos;
    }

    /**
     * 顶点
     */
    public static class Vertex<V, E> {
        V value;
        /** 以当前顶底为起始的边 **/
        Set<Edge<V, E>> fromEdges = new HashSet<>();
        /** 以当前顶底为终点的边 **/
        Set<Edge<V, E>> toEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex)) return false;
            Vertex<?, ?> vertex = (Vertex<?, ?>) o;
            return Objects.equals(value, vertex.value);// && Objects.equals(outEdges, vertex.outEdges) && Objects.equals(intEdges, vertex.intEdges)
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    /**
     * 边缘
     */
    public static class Edge<V, E> {
        /** 当前边的起点 **/
        public Vertex<V, E> from;
        /** 当前边的终点 **/
        public Vertex<V, E> to;
        public E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge)) return false;
            Edge<?, ?> edge = (Edge<?, ?>) o;
            return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);//  && Objects.equals(weight, edge.weight)
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    "}";
        }

        public Graph.EdgeInfo<V, E> toEdgeInfo() {
            Edge<V, E> edge = this;
            return new EdgeInfo<V, E>(edge.from.value, edge.to.value, edge.weight);
        }
    }
}
