package 算法.动态规划;

/**
 * @author like
 * @date 2021-01-18 15:33
 * @contactMe 980650920@qq.com
 * @description
 */
public class 最长上升子序列 {

    public static void main(String[] args) {
        System.out.println(lengthOfLis(new int[]{
                10, 2, 2, 5, 1, 7, 101, 18
        }));
    }

    private static int lengthOfLis(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for (int i = 0; i < dp.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue;
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
