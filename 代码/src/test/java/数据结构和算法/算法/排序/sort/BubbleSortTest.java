package 数据结构和算法.算法.排序.sort;

import java.util.Arrays;

class BubbleSortTest {

    public static void main(String[] args) {
        Integer[] array = new Integer[]{
                7, 4, 1, 5, 6, 8, 9
        };

        // 1.已经排好序的数组
        Integer[] sortArray = sort(array);

        // 2.插入一个数
        int insert = 2;

        // 判断是升序还是降序
        boolean asc = true;  // 小->大
        for (int i = 0; i < sortArray.length - 1; i++) {
            if (sortArray[i] > sortArray[i + 1]) { //  array[0] > array[1]  说明是 大->小
                asc = false;
                break;
            }
        }

        // 3.找到要插入的位置
        int index = 0;
        if (asc) { // 如果是 小->大
            for (int i = 0; i < sortArray.length; i++) {
                if (sortArray[i] < insert) {
                    index = i + 1;
                }
            }
        } else {
            for (int i = 0; i < sortArray.length; i++) {
                if (sortArray[i] < insert) {
                    index = i;
                }
            }
        }

        // 4.复制数组
        Integer[] newArray = new Integer[sortArray.length + 1];
        for (int i = 0; i < sortArray.length; i++) {
            if (i < index) {
                newArray[i] = sortArray[i];
            } else {
                newArray[i + 1] = sortArray[i];
            }
        }
        // 5.插入
        newArray[index] = insert;

        System.out.println("插入前："+Arrays.toString(sortArray));
        System.out.println("插入后："+Arrays.toString(newArray));
    }

    public static Integer[] sort(Integer[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int maxNumIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[maxNumIndex] > array[j]) {
                    maxNumIndex = j;
                }
            }
            Integer temp = array[i];
            array[i] = array[maxNumIndex];
            array[maxNumIndex] = temp;
        }
        return array;
    }
}