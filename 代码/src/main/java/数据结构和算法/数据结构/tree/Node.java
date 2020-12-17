package 数据结构和算法.数据结构.tree;

/**
 * @author like
 * @date 2020-12-15 12:09
 * @contactMe 980650920@qq.com
 * @description
 */
public class Node<T> {
    T element;
    Node<T> left;       // 当前节点的左子节点
    Node<T> right;     // 当前节点的右子节点
    Node<T> parent;   // 当前节点的父节点


    public Node(T element, Node<T> parent) {
        this.element = element;
        this.parent = parent;
    }

    public Node() {

    }

    @Override
    public String toString() {
        return "Node{" +
                "element=" + element +
                '}';
    }

    public boolean hasChildren() {
        return right != null && left != null;
    }

    public boolean isLeaf() {
        return right == null && left == null;
    }
}
