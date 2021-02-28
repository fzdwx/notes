package 算法.排序.sort;

import 算法.排序.sort.cmp.SelectionSort;
import 算法.排序.sort.nocmp.CountingSort;
import 算法.排序.tools.Student;

import java.text.DecimalFormat;

/**
 * @author like
 * @date 2020-12-28 10:50
 * @contactMe 980650920@qq.com
 * @description
 */
public abstract class Sort<E extends Comparable<E>> implements Comparable<Sort<E>> {
    private final DecimalFormat fmt = new DecimalFormat("#.00");
    protected E[] array;
    protected int cmpCount = 0;
    protected int swapCount = 0;
    private long time;

    protected abstract E[] sort();

    @Override
    public int compareTo(Sort<E> o) {
        int i = Math.toIntExact(time - o.time);
        return i == 0 ? cmpCount - o.cmpCount : i;
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\t"
                + stableStr + " \n"
                + "------------------------------------------------------------------";

    }

    public E[] sort(E[] array) {
        if (array == null || array.length < 2) throw new RuntimeException("size <2");
        this.array = array;
        long begin = System.currentTimeMillis();
        E[] sort = sort();
        time = System.currentTimeMillis() - begin;
        return sort;
    }

    protected int cmp(int i1, int i2) {
        return cmp(array[i1], array[i2]);
    }

    protected int cmp(E e1, E e2) {
        cmpCount++;
        return e1.compareTo(e2);
    }

    protected void swap(int i1, int i2) {
        swapCount++;

        E temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

    private boolean isStable() {
        //        if (this instanceof RadixSort) return true;
        if (this instanceof CountingSort) return false;
        //        if (this instanceof ShellSort) return false;
        if (this instanceof SelectionSort) return false;
        Student[] students = new Student[20];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(i * 10, 10);
        }
        this.sort((E[]) students);
        for (int i = 1; i < students.length; i++) {
            int score = students[i].score;
            int prevScore = students[i - 1].score;
            if (score != prevScore + 10) return false;
        }
        return true;
    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }
}
