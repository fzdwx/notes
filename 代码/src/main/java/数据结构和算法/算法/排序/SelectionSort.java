package 数据结构和算法.算法.排序;

import 数据结构和算法.算法.排序.tools.Asserts;
import 数据结构和算法.算法.排序.tools.Times;

import java.util.Arrays;
import java.util.Random;

/**
 * @author like
 * @date 2020-12-28 10:20
 * @contactMe 980650920@qq.com
 * @description 选择排序
 */
public class SelectionSort {
    public static final Integer ARRAY_SIZE = 10000;
    static Integer[] array;

    static {
        array = new Integer[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = new Random().nextInt(ARRAY_SIZE);
        }
    }

    public static void main(String[] args) {
        Integer[] a = Arrays.copyOf(array, array.length);
        Arrays.sort(array);
        Times.test("m-初始", () -> {
            m(a);
        });
        Asserts.test(Arrays.equals(a, array));
    }

    private static void m(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            int maxIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[maxIndex] > array[j]) {
                    maxIndex = j;
                }
            }
            int temp = array[maxIndex];
            array[maxIndex] = array[i];
            array[i] = temp;
        }
    }
}
