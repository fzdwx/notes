package 数据结构和算法.算法.动态规划;

import java.util.Arrays;

/**
 * @author like
 * @date 2021-01-17 11:37
 * @contactMe 980650920@qq.com
 * @description
 */
public class CoinChange {

    public static void main(String[] args) {
        System.out.println(coins(42));
        System.out.println(coins2(42));
    }

    /**
     * 暴力递归（自顶向下调用，出现重叠子问题）
     * 需要找零的钱的总额,有25,20,5,1等面值
     */
    public static int coins(int money) {
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

    /**
     * 记忆化搜索(自顶向下调用)
     */
    public static int coins2(int money) {
        if (money < 1) return -1;

        int[] dp = new int[money + 1];
        int[] faces = {1,5,20,25};
        for (int face : faces) {
            if (money <face) {
                break;
            }
            dp[face] = 1;
        }
        int i = coins2(money, dp);
        System.out.println(Arrays.toString(dp));
        return i;
    }

    private static int coins2(int money, int[] dp) {
        if (money < 1) return Integer.MAX_VALUE;
        if (dp[money] == 0) {
            dp[money] = Math.min(
                    Math.min(
                            coins2(money - 25, dp),
                            coins2(money - 20, dp)
                    ),
                    Math.min(
                            coins2(money - 5, dp),
                            coins2(money - 1, dp)
                    )) + 1;
        }
        return dp[money];
    }
}
