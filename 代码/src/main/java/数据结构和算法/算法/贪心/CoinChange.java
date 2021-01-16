package 数据结构和算法.算法.贪心;

import java.util.Arrays;

/**
 * @author like
 * @date 2021-01-16 11:00
 * @contactMe 980650920@qq.com
 * @description
 */
public class CoinChange {

    public static void main(String[] args) {
        Integer[] faces = {25, 10, 5, 1};
        int total = 41;
        coinChange(faces, total);
    }

    private static void coinChange(Integer[] faces,int total) {
        Arrays.sort(faces, (i1, i2) ->
                i2 - i1
        );
         int coins = 0, i = 0;
        while (i < faces.length) {
            if (total < faces[i]) {
                i++;
                continue;
            }
            System.out.println(faces[i]);
            total -= faces[i];
            coins++;
        }
        System.out.println(coins);
    }
}
