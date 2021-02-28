package 算法.动态规划;

/**
 * @author like
 * @date 2021-01-19 18:50
 * @contactMe 980650920@qq.com
 * @description
 */
public class 最长公共子序列 {

    public static void main(String[] args) {
        System.out.println(lcs(new int[]{
                1, 3, 5, 9, 10
        }, new int[]{
                1, 4, 9, 10
        }));
    }

    /** 优化 */
    public static int lcs(int[] arr1, int[] arr2) {
        if (arr1 == null || arr1.length == 0) return 0;
        if (arr2 == null || arr2.length == 0) { return 0;}
        int[] dp = new int[arr1.length + 1];
        for (int i = 1; i <= arr1.length; i++) {
            int curr = 0;
            for (int j = 1; j <= arr2.length; j++) {
                int leftTop = curr;
                curr = dp[j];
                if (arr1[i - 1] == arr2[j - 1]) {
                    dp[j] = leftTop + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
            }
        }
        return dp[arr2.length];
    }

    /**
     * 动态规划实现
     */
    public static int lcs2(int[] arr1, int[] arr2) {
        if (arr1 == null || arr1.length == 0) return 0;
        if (arr2 == null || arr2.length == 0) { return 0;}
        int[][] dp = new int[arr1.length + 1][arr2.length + 1];
        for (int i = 1; i <= arr1.length; i++) {
            for (int j = 1; j <= arr2.length; j++) {
                if (arr1[i - 1] == arr2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[arr1.length][arr2.length];
    }

    /**
     * 递归实现
     */
    public static int lcs1(int[] arr1, int[] arr2) {
        if (arr1 == null || arr1.length == 0) return 0;
        if (arr2 == null || arr2.length == 0) { return 0;}
        return lcs1(arr1, arr1.length, arr2, arr2.length);
    }

    private static int lcs1(int[] arr1, int i, int[] arr2, int j) {
        if (i == 0 || j == 0) return 0;
        if (arr1[i - 1] == arr2[j - 1]) {
            return lcs1(arr1, i - 1, arr2, j - 1) + 1;
        }
        return Math.max(lcs1(arr1, i - 1, arr2, j),
                lcs1(arr1, i, arr2, j - 1));
    }
}
