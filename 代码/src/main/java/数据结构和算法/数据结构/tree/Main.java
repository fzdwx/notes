package 数据结构和算法.数据结构.tree;


import 数据结构和算法.数据结构.tree.printer.BinaryTrees;
import 数据结构和算法.数据结构.tree.二叉树.AVLTree;
import 数据结构和算法.数据结构.tree.二叉树.BinarySearchTree;

import java.util.Random;

/**
 * @author like
 * @date 2020-12-15 12:46
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BinarySearchTree<Integer> avlTree = new AVLTree<>();
        int[] ints = {7, 8, 4, 5, 2, 3, 1, 9, 11, 10, 12};
        for (int i : ints) {
            avlTree.add(i);
        }
        BinaryTrees.println(avlTree);
        System.out.println("============remove================");
        avlTree.remove(9);
        BinaryTrees.println(avlTree);
        avlTree.remove(8);
        BinaryTrees.println(avlTree);
    }

    private static void test1() {
        boolean flag = true;
        while (flag) {
            BinarySearchTree<Integer> bTree = new BinarySearchTree<>();
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                bTree.add(random.nextInt(5));
            }
            if (bTree.isComplete()) {
                BinaryTrees.println(bTree);
                flag = false;
            }
        }
    }
}
