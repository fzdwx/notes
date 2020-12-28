package 数据结构和算法.算法.排序;

import 数据结构和算法.算法.排序.sort.BubbleSort;
import 数据结构和算法.算法.排序.sort.HeapSort;
import 数据结构和算法.算法.排序.sort.SelectionSort;
import 数据结构和算法.算法.排序.sort.Sort;
import 数据结构和算法.算法.排序.tools.Asserts;
import 数据结构和算法.算法.排序.tools.Times;

import java.util.Arrays;
import java.util.Random;

/**
 * @author like
 * @date 2020-12-28 11:23
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static final Integer ARRAY_SIZE = 10000;

    public static void main(String[] args) {
        Integer[] array = loadData();
        Integer[] sort = copy(array);Arrays.sort(sort);

        equals(sort, sort(SelectionSort.class.getName(), new SelectionSort<>(), copy(array)));
        equals(sort, sort(HeapSort.class.getName(), new HeapSort<>(), copy(array)));
        equals(sort, sort(BubbleSort.class.getName(), new BubbleSort<>(), copy(array)));
    }

    private static Integer[] loadData() {
        Integer[] array = new Integer[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = new Random().nextInt(ARRAY_SIZE);
        }
        return array;
    }

    public static Integer[] copy(Integer[] array) {
        return Arrays.copyOf(array, array.length);
    }

    public static Integer[] sort(String title, Sort<Integer> sort, Integer[] array) {
        Times<Integer[]> t = new Times<Integer[]>();
        return t.test(title, () -> sort.sort(array));
    }

    public static void equals(Integer[] array, Integer[] sort) {
        Asserts.test(Arrays.equals(array, sort));
    }

}
