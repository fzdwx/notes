package 数据结构和算法.算法.图;

import 数据结构和算法.算法.图.graph.Graph;
import 数据结构和算法.算法.图.graph.ListGraph;

import java.util.Map;

/**
 * @author like
 * @date 2021-01-05 11:22
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    static Graph.WeightManager<Double> weightManager = new Graph.WeightManager<Double>() {
        public int compare(Double w1, Double w2) {
            return w1.compareTo(w2);
        }

        public Double add(Double w1, Double w2) {
            return w1 + w2;
        }

        @Override
        public Double zero() {
            return 0.0;
        }

    };

    public static void main(String[] args) {
        Graph<Object, Double> g = directedGraph(Data.SP);
        System.out.println(g.shortestPath("A"));
        System.out.println("==================");
        Map<Object, Graph.PathInfo<Object, Double>> a = g.shortestPathLine("A");
        a.forEach((v,path)->{
            System.out.println(v+":"+path);
        });
    }

    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
            }
        }
        return graph;
    }

    /**
     * 无向图
     *
     * @param data
     * @return
     */
    private static Graph<Object, Double> undirectedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
                graph.addEdge(edge[1], edge[0]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
                graph.addEdge(edge[1], edge[0], weight);
            }
        }
        return graph;
    }
}
