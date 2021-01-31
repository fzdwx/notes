package 算法.动态规划;

/**
 * @author like
 * @date 2021-01-18 15:14
 * @contactMe 980650920@qq.com
 * @description
 */
public class 最大连续子序列和 {

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    private static int maxSubArray(int[] arrays) {
        if (arrays == null || arrays.length == 0) return -1;
        int dp = arrays[0];
        int max = dp;
        for (int i = 1; i < arrays.length; i++) {
            if (dp > 0) {
                dp = dp + arrays[i];
            } else {
                dp = arrays[i];
            }
            max = Math.max(dp, max);
        }
        return max;
    }

    private static int maxSubArray2(int[] arrays) {
        if (arrays == null || arrays.length == 0) return -1;
        int[] dp = new int[arrays.length];
        dp[0] = arrays[0];
        int max = dp[0];
        for (int i = 1; i < dp.length; i++) {
            if (dp[i - 1] <= 0) {
                dp[i] = arrays[i];
            } else {
                dp[i] = dp[i - 1] + arrays[i];
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }

}
