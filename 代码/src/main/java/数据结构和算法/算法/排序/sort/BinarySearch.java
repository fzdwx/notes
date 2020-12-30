package 数据结构和算法.算法.排序.sort;

/**
 * @author like
 * @date 2020-12-30 9:35
 * @contactMe 980650920@qq.com
 * @description 二分搜索
 */
public class BinarySearch {
    public static void main(String[] args) {
        Object[] array ={
                1,4,5,6
        };
        System.out.println(searchInsertValueIndex(array, 3));
    }
    /**
     * 在有序数组中查找元素的索引(存在该元素)
     *
     * @param array       数组
     * @param searchValue 搜索值
     * @return int
     */
    public static int indexOf(Object[] array, Object searchValue) {
        if (array == null || array.length == 0) {
            return -1;
        }
        int begin = 0;
        int end = array.length;
        while (begin < end) {  // 至少有一个元素
            int mid = (begin + end) >> 1;
            if (((Comparable) searchValue).compareTo(array[mid]) < 0) {  // V < m -> [begin,mid）
                end = mid;
            } else if (((Comparable) searchValue).compareTo(array[mid]) > 0) {  // V > m -> [mid+1,end）
                begin = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 搜索插入元素的索引(在有序数组中)
     *
     * @param array       数组
     * @param insertValue 插入的值
     * @return int
     */
    public static int searchInsertValueIndex(Object[] array, Object insertValue) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (((Comparable) insertValue).compareTo(array[mid]) < 0) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }


}
