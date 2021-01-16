package 数据结构和算法.算法.贪心;

import java.util.Arrays;

/**
 * @author like
 * @date 2021-01-16 10:48
 * @contactMe 980650920@qq.com
 * @description
 */
public class Pirate {

    public static void main(String[] args) {
        int[] weights = {3, 5, 4, 10, 7, 14, 2, 11};
        weights = Arrays.stream(weights).sorted().toArray();
        int capacity = 30, weight = 0, count = 0;
        for (int i = 0; i < weights.length && weight < capacity; i++) {
            int newWeight = weight + weights[i];
            if (newWeight <= capacity) {
                weight = newWeight;
                count++;
                System.out.println(weights[i]);
            }
        }
        System.out.println("共"+count+"件");
    }
}
