package 数据结构和算法.数据结构.list.linked.Ring;

import 数据结构和算法.数据结构.list.AbstractList;
import 数据结构和算法.数据结构.list.List;

/**
 * @author likeLove
 * @since 2020-10-01  14:30
 * 单向循环链表
 */
public class SinglyRingLinkedList<T> extends AbstractList<T> implements List<T> {
    /**
     * 链表的第一个节点
     */
    public Node<T> first;

    public static void main(String[] args) {
        SinglyRingLinkedList<Integer> list = new SinglyRingLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(0, 4);
      /*  System.out.println(list);
        list.remove(0);
        System.out.println(list);
        list.remove(2);
        System.out.println(list);*/
        System.out.print(list);
        System.out.print(list.first.o);
        System.out.print(list.first.next.o);
        System.out.print(list.first.next.next.o);
        System.out.print(list.first.next.next.next.o);
        System.out.print(list.first.next.next.next.next.o);

    }


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

            // 环形链表  获取当前链表的最后一个节点，size为0就是first
            Node<T> last = (size == 0) ? first : getNodeOfIndex(size );
            // 环形链表  最后一个节点指向first
            last.next = first;
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
            if (size == 1) {
                first = null;
            } else {
                // 环形链表 拿到最后一个节点
                Node<T> last = getNodeOfIndex(size - 1);

                // 首节点后移
                first = first.next;

                // 环形链表最后一个节点的next指向first
                last.next = first;
            }
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
