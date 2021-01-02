package 数据结构和算法.算法.排序.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author like
 * @date 2021-01-02 10:10
 * @contactMe 980650920@qq.com
 * @description 希尔排序
 */
public class ShellSort<E extends Comparable<E>> extends Sort<E> {


    @Override
    protected E[] sort() {
                List<Integer> stepSequence = getSedgewickStepSequence(); //{8,4,2,1}
                for (Integer step : stepSequence) {
                    sort(step);
                }
//        shellsortIS((Integer[]) array, array.length);
        return array;
    }

    int shellsortIS(Integer[] p, int n) {
        int op=0;
        int h,i,j,t,temp;
        for(t=1;t*t<=n/4;t+=t);
        for(h=n/4;t>0;t/=2,h=t*t-(3*t)/2+1){
            /*h=1,8,23,77,281,1073,4193,16577,
             *65921,262913,1050113...4^i+3*2^(i-1）+1*/
            for(i=h;i<n;i++){
                temp=p[i];
                for(j=i-h;j>=0&&p[j]>temp;j-=h){
                    p[j+h]=p[j];
                    op++;
                }
                p[j+h]=temp;
                op++;
            }
        }
        return op;
    }

    private List<Integer> getSedgewickStepSequence() {
        List<Integer> list = new ArrayList<>();
        int k = 0, step = 0;
        while (true) {
            if (k % 2 == 0) {
                int pow = (int) Math.pow(2, k >> 1);
                step = 1 + 9 * (pow * pow - pow);
            } else {
                int pow1 = (int) Math.pow(2, k >> 1);
                int pow2 = (int) Math.pow(2, k >> 1);
                step = 1 + 8 * pow1 * pow2 - 6 * pow2;
            }
            if (step >= array.length) break;
            list.add(0, step);
            k++;
        }
        return list;
    }

    private List<Integer> getShellStepSequence() {
        List<Integer> list = new ArrayList<>();
        int step = array.length;
        while ((step >>= 1) > 0) {    // 不断除以2
            list.add(step);
        }
        return list;
    }

    /**
     * 分成step列进行排序
     */
    private void sort(Integer step) {
        for (int col = 0; col < step; col++) { // 具体元素的索引：col  + row * step
            for (int i = col + step; i < array.length; i += step) {
                //                E e = array[i];
                //                int l = 0;
                //                int r = i;
                //                while (l < r) {
                //                    int mid = (l + r) >> 1;
                //                    if (cmp(i, mid) < 0) {
                //                        r = mid;
                //                    } else {
                //                        l = mid + 1;
                //                    }
                //                }
                //                // [l,i)
                //                for (int j = i; j > l; j -= step) {
                //                    array[j] = array[j - 1];
                //                }
                //                array[l] = e;
                int cur = i;
                while (cur > col && cmp(cur, cur - step) < 0) {
                    swap(cur, cur - step);
                    cur -= step;
                }
            }
        }
    }
}
