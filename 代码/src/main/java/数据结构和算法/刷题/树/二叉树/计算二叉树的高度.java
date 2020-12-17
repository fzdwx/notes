package 数据结构和算法.刷题.树.二叉树;

import 数据结构和算法.数据结构.tree.二叉树.BinarySearchTree;
import 数据结构和算法.数据结构.tree.printer.BinaryTrees;

import java.util.Random;

/**
 * @author like
 * @date 2020-12-16 13:16
 * @contactMe 980650920@qq.com
 * @description
 */
public class 计算二叉树的高度<T> {

    public static void main(String[] args) {
        BinarySearchTree<Integer> b = new BinarySearchTree<>();
        for (int i = 0; i < 30; i++) {
            b.add(new Random().nextInt(30));
        }
        BinaryTrees.println(b);
        System.out.println(b.height(1));
        System.out.println(b.height(2));
    }

}
