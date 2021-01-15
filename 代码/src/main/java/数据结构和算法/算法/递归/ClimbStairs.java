package 数据结构和算法.算法.递归;

/**
 * @author like
 * @date 2021-01-14 14:58
 * @contactMe 980650920@qq.com
 * @description  爬楼梯
 */
public class ClimbStairs {

    public int climbStairs(int n) {
        if (n <= 2) return n;
        return climbStairs(n-1)+climbStairs(n-2);
    }
}
