package 算法.并查集;

/**
 * @author like
 * @date 2021-01-04 12:20
 * @contactMe 980650920@qq.com
 * @description rank + 路径分裂
 */
public class QuickUnionRankPathSplit extends QuickUnionRankPathCompress {

    public QuickUnionRankPathSplit(int capacity) {
        super(capacity);
    }

    @Override
    protected int goFind(int v) {
        while (v != parents[v]) {
            int p = parents[v];
            parents[v] = parents[parents[v]];
            v = p;
        }
        return v;
    }
}
