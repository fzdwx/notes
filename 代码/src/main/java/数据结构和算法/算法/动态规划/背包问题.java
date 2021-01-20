package 数据结构和算法.算法.动态规划;

/**
 * @author like
 * @date 2021-01-20 9:41
 * @contactMe 980650920@qq.com
 * @description
 */
public class 背包问题 {

    public static int maxValue(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) { return 0;}
        int[] dp = new int[capacity + 1];
        for (int i = 1; i < values.length; i++) {
            for (int j = capacity; j >= 1; j--) {
                if (j < weights[i - 1]) continue;
                dp[j] = Math.max(dp[j], values[i - 1] + dp[j - weights[i + 1]]);
            }
        }
        return dp[capacity];
    }
}
