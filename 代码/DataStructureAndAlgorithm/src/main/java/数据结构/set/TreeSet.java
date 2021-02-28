package 数据结构.set;

import 数据结构.tree.二叉树.BinaryTree;
import 数据结构.tree.二叉树.RedBlackTree;

/**
 * @author like
 * @date 2020-12-23 10:55
 * @contactMe 980650920@qq.com
 * @description 红黑树实现的Set
 */
public class TreeSet<T> implements Set<T> {

    private final BinaryTree<T> tree = new RedBlackTree<>();

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.empty();
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(T element) {
        return tree.contains(element);
    }

    @Override
    public void add(T element) {
        tree.add(element);
    }

    @Override
    public void remove(T element) {
        tree.remove(element);
    }

    @Override
    public void traversal(Visitor<T> visitor) {
        BinaryTree.Visitor<T> tVisitor = new BinaryTree.Visitor<T>() {
            @Override
            public boolean visit(BinaryTree.Node<T> node) {
                return visitor.visit(node.element());
            }
        };
        tree.inorderTraversal(tVisitor);
    }
}
