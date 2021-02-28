# Set

特点

**不存放重复的元素**



## 接口

![image-20201223101654307](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201223101701.png) 





## 实现

可以使用

- 动态数组
- 链表
- 二叉树搜索树(AVL,RB树)





```java
public interface Set<T> {
    int size();

    boolean isEmpty();

    void clear();

    boolean contains(T element);

    void add(T element);

    void remove(T element);

    void traversal(Visitor<T> visitor);

    public static  interface Visitor<T> {
        boolean visit(T element);
    }
}
```





```java
public class ListSet<T> implements Set<T> {
    private final LinkedList<T> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(T element) {
        return list.contains(element);
    }

    @Override
    public void add(T element) {
        int index = list.indexOf(element);
        if (index != -1 && list.get(index) != null) {
            list.set(index, element);
        } else {
            list.add(element);
        }
    }

    @Override
    public void remove(T element) {
        int index = list.indexOf(element);
        if (index != -1) {
            list.add(element);
        }
    }

    @Override
    public void traversal(Visitor<T> visitor) {
        if (visitor == null) {
            return;
        }
        int length = list.size();
        for (int i = 0; i < length; i++) {
            boolean visit = visitor.visit(list.get(i));
            if (visit) { return; }
        }
    }
}
```







```java
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
```