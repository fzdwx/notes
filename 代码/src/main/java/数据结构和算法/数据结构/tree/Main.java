package 数据结构和算法.数据结构.tree;


import 数据结构和算法.数据结构.tree.printer.BinaryTrees;
import 数据结构和算法.数据结构.tree.二叉树.BinarySearchTree;
import 数据结构和算法.数据结构.tree.二叉树.BinaryTree;
import 数据结构和算法.数据结构.tree.二叉树.RedBlackTree;

import java.util.Random;

/**
 * @author like
 * @date 2020-12-15 12:46
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) throws Exception {
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        BinaryTree<Integer> tree = new BinarySearchTree<>();
        show(redBlackTree);
        System.out.println("==========二叉搜索树==========");
        show(tree);
    }

    private static void show(BinaryTree<Integer> bbst) {
        int[] ints = {6,2,4,7,9,3,4,1,10,5,12,17,22};
        for (int i : ints) {
//            System.out.println("****************{ "+i+" }***************");
            bbst.add(i);
        }
        BinaryTrees.println(bbst);
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
