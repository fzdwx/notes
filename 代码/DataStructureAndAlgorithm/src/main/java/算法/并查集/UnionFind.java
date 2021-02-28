package 算法.并查集;

/**
 * @author like
 * @date 2021-01-04 11:05
 * @contactMe 980650920@qq.com
 * @description
 */
public abstract class UnionFind {
    protected int[] parents;

    public UnionFind() {
        this(10);
    }

    public UnionFind(int capacity) {
        if (capacity < 0) {
            capacity = 10;
        }
        this.parents = new int[capacity];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
    }

    protected abstract void goUnion(int v1, int v2);

    protected abstract int goFind(int v);

    /**
     * 合并
     */
    public void union(int v1, int v2) {
        rangeCheck(v1);
        rangeCheck(v2);
        goUnion(v1, v2);
    }

    /**
     * 找到v所属的集合(根节点)
     */
    public int find(int v) {
        rangeCheck(v);
        return goFind(v);
    }

    /**
     * 查看2个元素的集合是不是一样的
     */
    public final boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }

    protected final void rangeCheck(int v) {
        if (v < 0 || v >= parents.length) {
            throw new IndexOutOfBoundsException("v:" + v + ",size:" + parents.length);
        }
    }

}
