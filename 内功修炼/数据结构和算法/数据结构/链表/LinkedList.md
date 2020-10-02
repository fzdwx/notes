链表 

# 一、单向链表

## 1.核心Api

根据索引返回节点

```java
private Node<T> getNodeOfIndex(int index) {
    Node<T> temp = first;

    //遍历节点
    for (int i = 0; i < index; i++) {
        temp = temp.next;
    }

    return temp;
}
```

## 2.T remove(int index)

-   找到需要删除的节点的前一个
-   使删除节点的下一个节点指向需要删除节点的下一个节点

```java
@Override
public T remove(int index) {
    checkIndex(index);

    Node<T> remove = first;
    if (index == 0) {
        //首节点后移
        first = first.next;
    } else {
        //删除节点的前一个
        Node<T> pre = getNodeOfIndex(index - 1);
        //删除的节点
        remove = pre.next;

        //删除节点
        pre.next = remove.next;
    }

    size--;
    return remove.o;
}
```



## 3.void add(int index, T o) 

-   找到需要添加节点的前一个
-   需要添加节点的next指向前节点的next
-   前节点的next指向添加的节点

```java
/**
 * 添加元素到链表的指定位置
 *
 * @param index 指定位置
 * @param o     元素
 */
@Override
public void add(int index, T o) {
    checkIndexOfAdd(index);

    if (index == 0) {
        //添加到链表的第一个
        first = new Node<>(o, first);
    } else {
        //添加节点的前一个节点
        Node<T> pre = getNodeOfIndex(index - 1);
        //需要添加的节点
        Node<T> add = new Node<>();
        //添加节点的下一个
        Node<T> next = pre.next;
        //保存元素
        add.o = o;

        //连接节点
        pre.next = add;
        add.next = next;
    }
    size++;
}
```



## 4.详情

```java
package 数据结构和算法.数据结构.list.linked;

import 数据结构和算法.数据结构.list.AbstractList;
import 数据结构和算法.数据结构.list.List;
import 数据结构和算法.数据结构.list.array.ArrayList;

/**
 * @author likeLove
 * @since 2020-10-01  14:30
 */
public class LinkedList<T> extends AbstractList<T> implements List<T> {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        System.out.println(list.size());
        list.add(1);
        System.out.println(list.size());
        list.add(2);
        list.add(3);
        System.out.println(list);
    }

    /**
     * 链表的第一个节点
     */
    private Node<T> first;

    /**
     * 查找元素在链表中的位置
     *
     * @param o 需要查找的元素
     *
     * @return 元素在链表中的索引
     */
    @Override
    public int indexOf(T o) {
        Node<T> temp = this.first;

        //遍历链表
        for (int i = 0; i < size; i++) {
            if (temp.o == o) {
                return i;
            }
            //后移
            temp = temp.next;
        }

        return 0;
    }

    /**
     * 添加元素到链表
     *
     * @param o 需要添加的元素
     */
    @Override
    public void add(T o) {
        add(size, o);
    }

    /**
     * 添加元素到链表的指定位置
     *
     * @param index 指定位置
     * @param o     元素
     */
    @Override
    public void add(int index, T o) {
        checkIndexOfAdd(index);

        if (index == 0) {
            //添加到链表的第一个
            first = new Node<>(o, first);
        } else {
            //添加节点的前一个节点
            Node<T> pre = getNodeOfIndex(index - 1);
            //需要添加的节点
            Node<T> add = new Node<>();
            //添加节点的下一个
            Node<T> next = pre.next;
            //保存元素
            add.o = o;

            //连接节点
            pre.next = add;
            add.next = next;
        }
        size++;
    }

    /**
     * 移除指定位置的元素
     *
     * @param index 指定位置
     *
     * @return 被移除的元素
     */
    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> remove = first;
        if (index == 0) {
            //首节点后移
            first = first.next;
        } else {
            //删除节点的前一个
            Node<T> pre = getNodeOfIndex(index - 1);
            //删除的节点
            remove = pre.next;

            //删除节点
            pre.next = remove.next;
        }

        size--;
        return remove.o;
    }

    /**
     * 移除指定元素
     *
     * @param o o
     *
     * @return 被移除的元素
     */
    @Override
    public T remove(T o) {
        return remove(indexOf(o));
    }

    /**
     * 根据索引返回元素(从0开始）
     *
     * @param index 元素的索引
     *
     * @return 元素
     */
    @Override
    public T get(int index) {
        checkIndex(index);

        return getNodeOfIndex(index).o;
    }

    /**
     * 为索引位置设置新的元素
     *
     * @param index 索引
     * @param o     新元素
     *
     * @return 旧元素
     */
    @Override
    public T set(int index, T o) {
        checkIndex(index);

        //找到相关节点
        Node<T> temp = getNodeOfIndex(index);
        T oldElement = temp.o;

        //替换元素
        temp.o = o;

        return oldElement;
    }

    /**
     * 清空链表
     */
    @Override
    public void clear() {
        size = 0;

        first = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> temp = this.first;
        for (int i = 0; i < size; i++) {
            sb.append(temp.o);
            if (i < size - 1) {
                sb.append(",").append(" ");
            }
            temp = temp.next;
        }
        sb.append("]");
        return String.valueOf(sb);
    }

    private Node<T> getNodeOfIndex(int index) {
        Node<T> temp = first;

        //遍历节点
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }

        return temp;
    }

    /**
     * 链表节点类
     *
     * @param <T>
     */
    private static class Node<T> {
        /**
         * 保存在节点中的元素
         */
        T o;
        /**
         * 当前节点的下一个节点
         */
        Node<T> next;

        public Node(T o, Node<T> next) {
            this.o = o;
            this.next = next;
        }

        public Node() { }
    }
}
```