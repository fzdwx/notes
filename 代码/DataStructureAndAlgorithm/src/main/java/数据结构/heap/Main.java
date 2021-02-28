package 数据结构.heap;

import 数据结构.tree.printer.BinaryTrees;

/**
 * @author like
 * @date 2020-12-27 11:00
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) {
        Integer[] list = new Integer[]{
                68, 72, 43, 50, 38, 100, 23
        };
        BinaryHeap<Integer> h = new BinaryHeap<>(list);
        BinaryTrees.println(h.topN(3));
    }
}
