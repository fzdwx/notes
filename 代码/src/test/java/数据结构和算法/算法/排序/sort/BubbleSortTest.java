package 数据结构和算法.算法.排序.sort;

import java.util.Arrays;

class BubbleSortTest {

    static Integer[] leftArray;
    static Integer[] array = {
            4, 2, 1, 5, 3, 7, 6, 8
    };
    static Integer[] stepArray = {
            4, 2, 1
    };

    public static void main(String[] args) {
        sort(0, array.length);
        System.out.println(Arrays.toString(array));
    }

    private static void sort(int begin, int end) {
        if (end - begin < 2) return;
        int mid = getPivot(begin, end);
        sort(begin, mid);
        sort(mid + 1, end);
    }

    private static int getPivot(int begin, int end) {
        Integer pivot = array[begin];

        end--;
        while (end > begin) {

            while (end > begin) {
                if (array[end] > pivot) {
                    end--;
                } else {
                    array[begin++] = array[end];
                    break;
                }
            }

            while (end > begin) {
                if (array[begin] < pivot) {
                    begin++;
                } else {
                    array[end--] = array[begin];
                    break;
                }
            }
        }


        array[begin] = pivot;
        return begin;
    }

}