package 数据结构和算法.算法.动态规划;

/**
 * @author like
 * @date 2021-01-17 11:37
 * @contactMe 980650920@qq.com
 * @description
 */
public class CoinChange {

    public static void main(String[] args) {
        System.out.println(coins(42));
    }

    /**
     * 暴力递归（自顶向下调用，出现重叠子问题）
     * 需要找零的钱的总额,有25,20,5,1等面值
     */
    static int coins(int money) {
        if (money < 1) {
            return Integer.MAX_VALUE;  // 如果不合法，就不用
        }
        if (money == 25 || money == 20 || money == 5 || money == 1) return 1;

        return Math.min(
                Math.min(
                        coins(money - 25),
                        coins(money - 20)
                ),
                Math.min(
                        coins(money - 5),
                        coins(money - 1)
                )) + 1;
    }
}
