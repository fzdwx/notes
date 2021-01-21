package 数据结构和算法.算法.布隆过滤器;

/**
 * @author like
 * @date 2021-01-21 14:44
 * @contactMe 980650920@qq.com
 * @description
 */
public class BloomFilter<T> {
    /** 二进制向量长度 **/
    private int bitSize;
    /** 二进制向量 **/
    private long[] bits;
    /** 散列的大小 **/
    private int hashSize;

    /**
     * 布隆过滤器
     *
     * @param n 数据规模
     * @param p 误判率
     */
    public BloomFilter(int n, double p) {
        if (n <= 0 || p <= 0 || p >= 1) {
            throw new IllegalArgumentException();
        }
        double ln2 = Math.log(2);
        // 1.求出二进制向量长度
        bitSize = (int) -((n * Math.log(p)) / (ln2 * ln2));
        // 2.求出哈希函数的个数
        hashSize = (int) (bitSize / ln2 / n);
        // 3.bits
        bits = new long[bitSize + Long.SIZE - 1 / Long.SIZE];
        System.out.println("bitSize = " + bitSize);
        System.out.println("hashSize = " + hashSize);
    }

    public static void main(String[] args) {
        new BloomFilter<Integer>(1_000_000, 0.01);
    }

    public void put(T value) {
        int hash = value.hashCode();
        int hash2 = hash >>> 16;
        for (int i = 0; i < hashSize; i++) {
            int combineHash = hash + (i * hash2);
            if (combineHash < 0) {
                combineHash = ~combineHash;
            }
            // 1.生成一个二进制索引
            int index = combineHash % bitSize;
            // 2.设置为1
            setBit(index);
        }
    }

    public boolean contains(T value) {
        int hash = value.hashCode();
        int hash2 = hash >>> 16;
        for (int i = 0; i < hashSize; i++) {
            int combineHash = hash + (i * hash2);
            if (combineHash < 0) {
                combineHash = ~combineHash;
            }
            // 1.生成一个二进制索引
            int index = combineHash % bitSize;
            // 2.查询index是否为0
            return get(index) == 0;
        }
        return true;
    }

    private int get(int index) {
        return 0;
    }

    private void setBit(int index) {

    }
}
