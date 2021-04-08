package 算法.A4.c1;

import 算法.A4.edu.princeton.cs.algs4.In;
import 算法.A4.edu.princeton.cs.algs4.StdIn;
import 算法.A4.edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.List;

/**
 * Create By like On 2021-04-08 21:38
 * 二分搜索
 */
public class BinarySearch {

    public static int rank(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
       /* int[] whiteList = In.readInts();
        Arrays.sort(whiteList);

        while (!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            if (rank(key, whiteList)==-1) {
                StdOut.println(key);
            }
        }*/
        int[] ints = new int[]{1, 3, 4, 5, 7,768, 6};
        Arrays.sort(ints);

        System.out.println(rank(5, ints));
    }
}
