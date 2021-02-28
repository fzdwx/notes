package 算法.并查集;

/**
 * @author like
 * @date 2021-01-04 12:08
 * @contactMe 980650920@qq.com
 * @description 基于rank+路径压缩
 */
public class QuickUnionRankPathCompress extends UnionFind {
    private int[] ranks;

    public QuickUnionRankPathCompress(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        for (int i = 0; i < ranks.length; i++) {
            ranks[i] = 1;
        }
    }

    @Override
    protected void goUnion(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        if (ranks[p1] < ranks[p2]) {
            parents[p1] = p2;
        } else if (ranks[p1] > ranks[p2]) {
            parents[p2] = p1;
        } else {
            parents[p1] = p2;
            ranks[p2] += 1;
        }
    }

    @Override
    protected int goFind(int v) {
        if (parents[v] != v) {
            parents[v] = goFind(parents[v]);
        }
        return parents[v];
    }
}
