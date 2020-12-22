package 数据结构和算法.数据结构.tree;


import 数据结构和算法.数据结构.tree.printer.BinaryTrees;
import 数据结构和算法.数据结构.tree.二叉树.*;

import java.util.Random;

/**
 * @author like
 * @date 2020-12-15 12:46
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BinaryBalanceSearchTree<Integer> redBlackTree = new RedBlackTree<>();
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        BinaryBalanceSearchTree<Integer> avl = new AVLTree<>();
        loadData(redBlackTree);
        loadData(bst);
        loadData(avl);
        BinaryTrees.println(bst);
        BinaryTrees.println(avl);
        BinaryTrees.println(redBlackTree);
    }

    private static void loadData(BinaryTree<Integer> bbst) {
        int[] ints = {10,35,47,11,5,57,39,14,27,84,75,63,41,37,24,96};
        for (int i : ints) {
            bbst.add(i);
        }
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
