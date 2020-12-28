package 数据结构和算法.算法.排序.sort;

/**
 * @author like
 * @date 2020-12-28 8:40
 * @contactMe 980650920@qq.com
 * @description 冒泡排序
 */
public class BubbleSort<E> extends Sort<E> {

    @Override
    protected E[] sort() {
        m();
        return array;
    }

    private void m() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length; j++) {
                if (cmp(i, j) > 0) {
                    swap(i, j);
                }
            }
        }
    }

    private void m1() {
        for (int i = 0; i < array.length - 1; i++) {
            boolean sorted = true;
            for (int j = i; j < array.length; j++) {
                if (cmp(i, j) > 0) {
                    swap(i, j);
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }

    private void m2() {
        int len = array.length - 1;
        int index = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (cmp(j, j + 1) > 0) {
                    swap(j, j + 1);
                    index = j;
                }
            }
            len = index;
        }
    }

}
