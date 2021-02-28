package 算法.排序;

import java.util.Arrays;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-28 12:33
 */
public class Demo_01 {
    public static void main(String[] args) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    int temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(arr));
    }

    static int[] arr = {3, 4, 1, 2, 5, 81, 999, 23, 12, 44, 6};


}
