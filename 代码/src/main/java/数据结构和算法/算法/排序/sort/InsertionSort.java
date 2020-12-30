package 数据结构和算法.算法.排序.sort;

/**
 * @author like
 * @date 2020-12-30 8:47
 * @contactMe 980650920@qq.com
 * @description 插入排序
 */
public class InsertionSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected E[] sort() {
        for (int start = 1; start < array.length; start++) {   // 1.假设排好序的数组是 array[0]
            int cur = start;                                  // 2.记录取出元素的index
            while (cur > 0 && cmp(cur, cur - 1) < 0) {       // 3.比较 array[cur] 是不是小于 array[cur-1]
                swap(cur, cur - 1);                         // 4.交换
                cur--;                                     // 5.继续交换 -> 直到大于
            }
        }
        return array;
    }
}
