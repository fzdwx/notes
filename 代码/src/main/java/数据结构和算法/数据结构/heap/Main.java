package 数据结构和算法.数据结构.heap;

import 数据结构和算法.数据结构.tree.printer.BinaryTrees;

import java.util.Arrays;

/**
 * @author like
 * @date 2020-12-27 11:00
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) {
        BinaryHeap<Integer> h = new BinaryHeap<>();
        Integer[] list = new Integer[]{
                68, 72, 43, 50, 38,100,23
        };
        for (Integer i : list) {
            h.add(i);
        }
        BinaryTrees.println(h);
        h.replace(11);
        BinaryTrees.println(h);
        System.out.println(Arrays.toString(h.elements));
    }
}
