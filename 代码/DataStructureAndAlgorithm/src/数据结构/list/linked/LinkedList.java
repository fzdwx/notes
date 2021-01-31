package 数据结构.list.linked;

import 数据结构.list.AbstractList;
import 数据结构.list.List;


/**
 * @author likeLove
 * @since 2020-10-03  19:58
 * 双向链表
 * - 含有头节点和尾节点
 * - 节点类含有prev 和 next 两个指针
 */
public class LinkedList<T> extends AbstractList<T> implements List<T> {
    /**
     * 链表的第一个节点 The First.
     */
    private Node<T> first;
    /**
     * 最后一个节点 The Last.
     */
    private Node<T> last;


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

    /**
     * 查找元素在数组中的位置
     *
     * @param o 需要查找的元素
     * @return 元素在数组中的索引
     */
    @Override
    public int indexOf(T o) {
        Node<T> temp = this.first;
        int index = 0;

        while (temp != null) {
            if (temp.o.equals(o)) {
                break;
            }
            index++;
            temp = temp.next;
        }

        return temp == null ? -1 : index;
    }

    /**
     * 添加元素到数组
     *
     * @param o 需要添加的元素
     */
    @Override
    public void add(T o) {
        add(size, o);
    }

    /**
     * 添加元素到数组的指定位置
     * 普通添加
     * 添加前   prev  next
     * 添加后   prev  newCurr next
     * <p>
     * 添加到最后
     * 添加前   last
     * 添加后   last newLast
     *
     * @param index 指定位置
     * @param o     元素
     */
    @Override
    public void add(int index, T o) {
        // 检查索引是否越界
        checkIndexOfAdd(index);
        if (index == size) {  // 添加到最后面
            // 获取前驱，因为是最后一个节点所以没有后继
            Node<T> prev = last;

            // 新的last
            last = new Node<>(o, prev, null);

            // 判断是否是添加的第一个节点
            if (prev == null) {
                // 因为是第一个节点，所以first和last都指向同一个节点
                first = last;
            } else {
                // 旧last的next指向新last
                prev.next = last;
            }
        } else {   // 添加到中间和前面
            // 获取添加节点的前驱和后继
            Node<T> next = getNodeOfIndex(index);
            Node<T> prev = next.prev;

            // 添加节点
            Node<T> newCurr = new Node<>(o, prev, next);

            // 前驱和后继和新添加的节点连线
            next.prev = newCurr;

            // 判断是否是添加到第一个第一个节点
            if (prev != null) { //  不是
                prev.next = newCurr;
            } else {  // 是
                first = newCurr;
            }
        }

        size++;
    }

    /**
     * 移除指定位置的元素
     * prev remove next
     * prev next
     *
     * @param index 指定位置
     * @return 被移除的元素
     */
    @Override
    public T remove(int index) {
        // 检查索引是否越界
        checkIndex(index);

        // 获取要删除的节点以及前驱和后继
        Node<T> remove = getNodeOfIndex(index);
        Node<T> prev = remove.prev;
        Node<T> next = remove.next;

        // 判断是否删除的是first
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            //remove.prev = null;
        }

        // 判断删除的是否是last
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            //remove.next = null;
        }

        size--;
        return remove.o;
    }

    /**
     * 移除指定元素
     *
     * @param o o
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
     * @return 旧元素
     */
    @Override
    public T set(int index, T o) {
        // 检查index
        checkIndex(index);

        // 获取需要修改的节点
        Node<T> set = getNodeOfIndex(index);
        // 节点存放的旧元素
        T oldO = set.o;

        // 修改
        set.o = o;

        //返回旧元素
        return oldO;
    }

    /**
     * 清空数组
     */
    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
        System.gc();
    }

    /**
     * 根据索引返回node节点
     *
     * @param index 索引
     * @return 根据节点返回的节点
     */
    private Node<T> getNodeOfIndex(int index) {
        Node<T> temp;

        // 寻找这个节点
        if (index < (size >> 1)) {
            // 1.index < 元素个数的一半，就从first开始向后寻找。
            //   从0遍历到index
            temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            // 2.index > 元素个数的一半，就从last开始向前寻找
            //   从最后一个元素遍历到index
            temp = last;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }

        return temp;
    }

    /**
     * 双向链表的节点类
     *
     * @param <T> the type parameter
     */
    public static class Node<T> {
        /**
         * 元素
         */
        T o;
        /**
         * 前驱
         */
        Node<T> prev;
        /**
         * 后继
         */
        Node<T> next;

        /**
         * 初始化 new Node.
         *
         * @param o    当前节点存放的元素
         * @param prev 当前节点的前驱
         * @param next 当前节点的后继
         */
        public Node(T o, Node<T> prev, Node<T> next) {
            this.o = o;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return o.toString();
        }
    }


}
