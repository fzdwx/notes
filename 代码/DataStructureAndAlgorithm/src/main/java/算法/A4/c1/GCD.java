package 算法.A4.c1;

/**
 * Create By like On 2021-04-08 21:31
 * 欧几里得算法
 */
public class GCD {

    /**
     * 计算两个非负整数，p和q的最大公约数：
     *  若q是0，则最大公约数是p。
     *  否则将p除以q得到余数r，p和q的最大公约数就是q和r的最大公约数
     * @param q 问
     * @param p p
     * @return int
     */
    public static int gcd(int p, int q) {
        if (q == 0) return p;
        int r = p % q;

        return gcd(q,r);
    }

    public static void main(String[] args) {
        gcd(3,10);
    }
}
