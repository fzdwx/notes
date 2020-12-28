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
        //        m1();
        //        m2();
        return array;
    }

    private void m() {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (cmp(i, j) > 0) {
                    swap(i, j);
                }
            }
        }
    }

    private void m1() {
        for (int i = 0; i < array.length; i++) {
            boolean sorted = true;
            for (int j = i; j < array.length - 1; j++) {
                if (cmp(i, j) > 0) {
                    swap(i, j);
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }

    private void m2() {
        for (int end = array.length - 1; end > 0; end--) {
            int index = 1;
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(begin, begin - 1) > 0) {
                    swap(begin, begin - 1);
                    index = begin;
                }
            }
            end = index;
        }
    }

}
