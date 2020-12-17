





链表 

# 单向链表

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







# 双向链表

含有一个头节点和尾节点的链表

-   头节点：链表的第一个元素
-   尾节点：链表的最后一个元素
-   节点类
    -   prev指针
    -   next指针
    -   o



## 1、clear

思路：

-   size归o
-   first清空
-   last清空



## 2、add

```
普通添加
添加前   prev  next
添加后   prev  newCurr next

添加到最后
添加前   last
添加后   last newLast
```

-   判断是否是添加到最后

    -   获取旧的last
    -   创建新的last，新last的prev指向旧last
    -   判断链表是否为空
        -   如果为空，则first=last
        -   不为空，旧last的next指向新last

-   添加到其他位置

    -   获取这个节点的前驱与后继
    -   创建需要添加的节点，指向前驱与后继
    -   后继的prev指向新创建的节点
    -   判断是否是添加到最前面
        -   只需要判断前驱是否为空
        -   不为空，前驱的next指向新添加的节点
        -   为空，就让first指向新添加的节点

    

## 3、remove

~~~
 prev remove next
 prev next
~~~

-   获取要删除的节点
-   获取该节点的前驱与后继
-   前驱和后继互连
    -   判断前驱是否为空
        -   说明要删除是first
        -   first直接指向next
    -   判断next是否为空
        -   说明要删除的是last
        -   last直接指向prev









## 代码

```java
package 数据结构和算法.数据结构.list.linked;

import 数据结构和算法.数据结构.list.AbstractList;
import 数据结构和算法.数据结构.list.List;

import java.util.Objects;


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

    /**
     * 查找元素在数组中的位置
     *
     * @param o 需要查找的元素
     *
     * @return 元素在数组中的索引
     */
    @Override
    public int indexOf(T o) {
        Node<T> temp = this.first;
        int index = 0;

        while (temp != null) {
            if (temp.o == o) {
                break;
            }
            index++;
            temp = temp.next;
        }

        return index;
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
     *
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
        }

        // 判断删除的是否是last
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
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
     * 根据索引返回node节点
     *
     * @param index 索引
     *
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
```





## 测试：

~~~java
public static void main(String[] args) {
        int j = 100000;
        LinkedList<Integer> list = new LinkedList<>();
        java.util.LinkedList<Integer> sysList = new java.util.LinkedList<>();
        long listStart = System.currentTimeMillis();
        for (int i = 1; i <= j; i++) {
            list.add(i);
            if (i > 100) {
                list.add(i-10,i);
            }
        }
        long listEnd = System.currentTimeMillis();
        System.out.println(listEnd  - listStart);
        long sysStart = System.currentTimeMillis();
        for (int i = 1; i <= j; i++) {
            sysList.add(i);
            if (i > 100) {
                sysList.add(i - 10, i);
            }
        }
        long sysEnd = System.currentTimeMillis();
        System.out.println(sysEnd - sysStart);

        System.out.println(Objects.equals(list.toString(), sysList.toString()));
    }	
~~~

![image-20201006145807224](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201006145807.png)







# 单向循环链表

最后一个节点指向第一个节点



## add

![image-20201006162732468](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201006162732.png)



## remove![image-20201006161559527](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201006161559.png)





# 双向循环链表

## add

![image-20201006182818571](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201006182818.png)



## remove

![image-20201006190743462](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201006190743.png)









## 约瑟夫问题



### 在双向循环链表中添加属性和方法



```java
/**
 * 针对约瑟夫问题
 * 当前的节点
 */
private Node<T> current;

/**
 * 针对约瑟夫问题
 * 重置current节点，指向first
 */
public void resetCurrent() {
    current = first;
}

/**
 * 针对约瑟夫问题
 * 当前下
 *
 * @return {@link T}
 */
public T currentNext() {
    if (current == null) return null;

    current = current.next;
    return current.o;
}

/**
 * 针对约瑟夫问题
 * 删除当前
 */
public T removeCurrent() {
    Node<T> next = current.next;
    T o = remove(current.o);
    if (size == 0) {
        current = null;
    } else {
        current = next;
    }
    return o;
}
```



### 实现

```java
public static void main(String[] args) {
    LinkedRingList<Integer> list = new LinkedRingList<>();

    for (int i = 1; i <= 8; i++) {
        list.add(i);
    }
    System.out.println("添加完成：" + list);

    int count = 3;

    josephQuestion(list, count);
}

private static void josephQuestion(LinkedRingList<Integer> list, int count) {
    // 1.使curr 指向 first
    list.resetCurrent();
    // 2.小孩出圈
    while (list.size > 0) {
        // 3.数几次出一个
        for (int i = 1; i < count; i++) {
            // 4.出
            list.currentNext();
        }
        System.out.println(list.removeCurrent());
    }
}
```