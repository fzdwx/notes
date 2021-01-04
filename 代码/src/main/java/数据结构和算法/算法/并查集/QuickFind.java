package 数据结构和算法.算法.并查集;

/**
 * @author like
 * @date 2021-01-04 10:51
 * @contactMe 980650920@qq.com
 * @description
 */
public class QuickFind extends UnionFind {
    public QuickFind() {
    }

    @Override
    protected void goUnion(int v1, int v2) {
        int p1 = goFind(v1);
        int p2 = goFind(v2);
        if (p1 == p2) return;
        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == p1) {
                parents[i] = p2;
            }
        }
    }

    /**
     * 找到v所属的集合(根节点)
     */
    @Override
    protected int goFind(int v) {
        return parents[v];
    }


}
