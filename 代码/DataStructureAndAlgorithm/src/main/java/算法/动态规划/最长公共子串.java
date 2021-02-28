package 算法.动态规划;

/**
 * @author like
 * @date 2021-01-20 9:04
 * @contactMe 980650920@qq.com
 * @description
 */
public class 最长公共子串 {

    public static void main(String[] args) {
        System.out.println(lcs("ABDCBA", "BABCA"));
    }

    public static int lcs(String s1, String s2) {
        if (s1 == null || s2 == null) return 0;
        char[] c1 = s1.toCharArray();
        if (c1.length == 0) {return 0; }
        char[] c2 = s2.toCharArray();
        if (c2.length == 0) {return 0; }
        int[][] dp = new int[c1.length + 1][c2.length + 1];
        int max = 0;
        for (int i = 1; i <= c1.length; i++) {
            for (int j = 1; j <= c2.length; j++) {
                if (c1[i - 1] != c2[j - 1]) continue;
                dp[i][j] = dp[i - 1][j - 1] + 1;
                max = Math.max(dp[j][j], max);
            }
        }
        return max;
    }
}
