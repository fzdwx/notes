package 数据结构和算法.数据结构.tree;


import 数据结构和算法.数据结构.tree.printer.BinaryTrees;

import java.util.Random;

/**
 * @author like
 * @date 2020-12-15 12:46
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BinarySearchTree<Integer> bTree = new BinarySearchTree<>();
        int[] ints = {7, 8, 4, 5, 2, 3, 1, 9, 11, 10, 12};
        for (int i : ints) {
            bTree.add(i);
        }
        BinaryTrees.println(bTree);
        bTree.remove(11);
        System.out.println("============");
        BinaryTrees.println(bTree);
    }

    private static void test1() {
        boolean flag = true;
        while (flag) {
            BinarySearchTree<Integer> bTree = new BinarySearchTree<>();
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                bTree.add(random.nextInt(5));
            }
            if (bTree.isComplete2()) {
                BinaryTrees.println(bTree);
                flag = false;
            }
        }
    }
}
