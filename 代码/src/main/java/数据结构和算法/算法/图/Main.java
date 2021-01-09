package 数据结构和算法.算法.图;

/**
 * @author like
 * @date 2021-01-05 11:22
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        Graph<String, Integer> g = new ListGraph<>();
        g.addEdge("v1", "v0", 9);
        g.addEdge("v1", "v2", 3);
        g.addEdge("v2", "v0", 2);
        g.addEdge("v2", "v3", 5);
        g.addEdge("v3", "v4", 1);
        g.addEdge("v0", "v4", 6);
        g.bfs("v1", s -> {
            System.out.print(s);
            return false;
        });
        System.out.println();
        g.dfs("v1", s -> {
            System.out.print(s);
            return false;
        });
        System.out.println();
        g.dfs2("v1", s -> {
            System.out.print(s);
            return false;
        });
    }
}
