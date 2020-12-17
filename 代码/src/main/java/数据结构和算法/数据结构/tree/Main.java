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
        int[] ints = {7, 8, 4, 5, 1, 9, 11};
        for (int i : ints) {
            bTree.add(i);
        }
        BinaryTrees.println(bTree);
        Node<Integer> node = bTree.getNodeForElement(12);
        System.out.println(bTree.predecessor(node));
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
