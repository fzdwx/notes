package 数据结构和算法.刷题.动态规划;

import java.util.Arrays;

/**
 * @author like
 * @date 2021-01-27 12:42
 * @contactMe 980650920@qq.com
 * @description
 */
public class _121买卖股票的最佳时机 {
    public static void main(String[] args) {
        solution(new int[]{
                7, 1, 5, 3, 6, 4
        });
    }


    public static int solution2(int[] arr) {
        if (arr == null || arr.length <= 0) return -1;
        int[] dp = new int[arr.length];
        for (int i = 1; i < arr.length; i++) {
            dp[i - 1] = arr[i] - arr[i - 1];
        }
        System.out.println(Arrays.toString(dp));
        return 0;
    }

    public static int solution(int[] arr) {
        if (arr == null || arr.length <= 0) return -1;

        int minPrice = arr[0];  // 股票在这几天里面的最低价格
        int profits = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < minPrice) {
                minPrice = arr[i];
            } else {
                profits = Math.max(profits, arr[i] - minPrice);
            }
        }
        return profits;
    }
}
