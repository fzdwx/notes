package 算法.并查集;

/**
 * @author like
 * @date 2021-01-04 11:24
 * @contactMe 980650920@qq.com
 * @description
 */
public class QuickUnion extends UnionFind {

    public QuickUnion() {
    }

    @Override
    protected void goUnion(int v1, int v2) {
        int p1 = goFind(v1);
        int p2 = goFind(v2);
        if (p1 == p2) return;
        parents[p1] = p2;
    }


    @Override
    protected int goFind(int v) {
        // 找到根节点
        while (v != parents[v]) {
            v = parents[v];
        }
        return v;
    }
}
