package 数据结构和算法.算法.排序;

/**
 * @author like
 * @date 2020-12-28 8:40
 * @contactMe 980650920@qq.com
 * @description 冒泡排序
 */
public class BubbleSort {
    static int[] array = new int[]{
            3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48
    };

    public static void main(String[] args) {
        m2();
    }

    private static void m2() {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length - 1; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }

            }
        }

    }

    private static void m1() {
        int counter = 0;
        boolean flag;
        do {
            flag = false;
            for (int i = 1; i < array.length; i++) {
                if (array[i] < array[i - 1]) {
                    int temp = array[i];
                    array[i] = array[i - 1];
                    array[i - 1] = temp;
                    flag = true;
                }
                counter++;
            }
        } while (flag);
        System.out.println(counter);
    }
}
