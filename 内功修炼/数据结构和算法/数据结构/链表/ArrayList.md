数组列表

# 1.接口设计

```java
package 修炼.数据结构和算法.数据结构.List;

/**
 * @author likeLove
 * @since 2020-09-29  20:54
 * 动态数组的接口设计
 */
public interface DynamicArray<T> {
    /**
     * 获取数组元素个数
     *
     * @return 当前数组元素个数
     */
    int size();

    /**
     * 查找元素在数组中的位置
     *
     * @param o 需要查找的元素
     *
     * @return 元素在数组中的索引
     */
    int indexOf(Object o);

    /**
     * 判断元素是否存在
     *
     * @param o 元素
     *
     * @return 返回这个元素是否存在
     */
    boolean contains(Object o);

    /**
     * 判断数组是否为空
     *
     * @return 数组是否为空
     */
    boolean isEmpty();


    /**
     * 添加元素到数组
     *
     * @param o 需要添加的元素
     */
    void add(Object o);

    /**
     * 添加元素到数组的指定位置
     *
     * @param index 指定位置
     * @param o     元素
     */
    void add(int index, Object o);

    /**
     * 移除指定位置的元素
     *
     * @param index 指定位置
     *
     * @return 被移除的元素
     */
    T remove(int index);

    /**
     * 移除指定元素
     * @param o o
     * @return 被移除的元素
     */
    T remove(Object o);

    /**
     * 根据索引返回元素(从0开始）
     *
     * @param index 元素的索引
     *
     * @return 元素
     */
    T get(int index);

    /**
     * 为索引位置设置新的元素
     *
     * @param index 索引
     * @param o     新元素
     *
     * @return 旧元素
     */
    T set(int index, Object o);

    /**
     * 清空数组
     */
    void clear();
}
```



# 2.成员变量设计

```java
    public class ArrayList<T> implements DynamicArray<T> {

    ···
        
    /**
 	* 当前数组元素个数
 	*/
    private int size;
    /**
 	* 存放元素的数组
	 */
    private Object[] elements;
    /**
 	* 默认数组大小
 	*/
    public static final int DEFAULT_CAPACITY = 10;

}
```



# 3.构造函数

```java
public class ArrayList<T> implements DynamicArray<T> {
    ···
    /**
	 * 初始化数组大小
	 * @param capacity 数组容量
 	*/
    public ArrayList(int capacity) {
        //如果传入的数组容量大小  小于  默认的 10，就直接初始化为10
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = new Object[capacity];
    }

    public ArrayList() {
        //如果不传入初始化数组容量
        this(DEFAULT_CAPACITY);
    }
    ···
}
```



# 4.完善方法

## a.int indexOf(Object o) 

循环遍历数组

```java
@Override
public int indexOf(Object o) {
    // 循环遍历,判断 o 是否等于数组中对应的元素
    for (int i = 0; i < size; i++) {
        if (elements[i].equals(o)) {
            return i;
        }
    }
    return -1;
}
```



## b.void add(int index, Object o)

-   从数组的最后一个向前遍历到要插入的索引
-   向后移一位
-   插入元素
-   size++

```java
@Override
public void add(int index, Object o) {
    // TODO likelove: 2020/10/1  动态扩容
    checkIndexOfAdd(index);
    for (int i = size; i > index; i--) {
        elements[i + 1] = elements[i];
    }
    elements[index] = o;
    size++;
}
```





## c.T remove(int index)

-   从索引位置开始遍历数组到size
-   让当前元素等于当前元素的后一个
-   size- - 

```java
@Override
public T remove(int index) {
    checkIndex(index);
    Object remove = elements[index];
    for (int i = index; i < size; i++) {
        elements[i] = elements[i + 1];
    }
    size--;
    return (T) remove;
}
```



## d.String toString()

```java
@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb./*append("size = ").append(size).*/append("[");
    for (int i = 0; i < size; i++) {
        sb.append(elements[i]);
        if (i < size - 1) {
            sb.append(",").append(" ");
        }
    }
    sb.append("]");
    return String.valueOf(sb);
}
```



## f.void checkIndex(int index)

```java
/**
 * 检查索引是否越界
 * @param index 索引
 */
private void checkIndex(int index) {
    if (index < 0 || index >= size) {
        outOfBoundsException(index);
    }
}

/**
 * 检查索引是否越界（仅限添加方法）
 * @param index 索引
 */
private void checkIndexOfAdd(int index) {
    if (index < 0 || index > size) {
        outOfBoundsException(index);
    }
}

/**
 * 抛出数组越界异常
 * @param index 索引
 */
private void outOfBoundsException(int index) {
    throw new IndexOutOfBoundsException("index = " + index + "," + "size = " + size);
}
```



# 5.动态扩容

-   判断输入的容量是否大于现在数组的长度
-   扩容1.5倍
-   创建新数组并复制旧数组中的内容

```java
private void expandCapacity(int needCapacity) {
    int oldCapacity = elements.length;
    if (oldCapacity >= needCapacity) {
        return;
    }
    int newCapacity = (oldCapacity >> 1) + oldCapacity;
    Object[] newElements = new Object[newCapacity];
    for (int i = 0; i < size; i++) {
        newElements[i] = elements[i];
    }
    elements = newElements;
    System.out.println(oldCapacity+"扩容为 " + newCapacity);
}
```



# 6.详细代码

```java
package 数据结构和算法.数据结构.list.array;

import 数据结构和算法.数据结构.list.AbstractList;
import 数据结构和算法.数据结构.list.List;


/**
 * @author likeLove
 * @since 2020-09-29  20:49
 */
public class ArrayList<T> extends AbstractList<T> implements List<T> {
    /**
     * 当前数组元素个数
     */
    private int size;

    /**
     * 存放元素的数组
     */
    private T[] elements;

    /**
     * 默认数组大小
     */
    public static final int DEFAULT_CAPACITY = 10;

    /**
     * 初始化数组大小
     *
     * @param capacity 初始化容量
     */
    public ArrayList(int capacity) {
        //如果传入的数组容量大小  小于  默认的 10，就直接初始化为10
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = (T[]) new Object[capacity];
    }

    public ArrayList() {
        //如果不传入初始化数组容量
        this(DEFAULT_CAPACITY);
    }


    /**
     * 查找元素在数组中的位置
     *
     * @param o 需要查找的元素
     *
     * @return 元素在数组中的索引
     */
    @Override
    public int indexOf(T o) {
        // 循环遍历,判断 o 是否等于数组中对应的元素
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 添加元素到数组的最后
     *
     * @param o 需要添加的元素
     */
    @Override
    public void add(T o) {
        //  调用add
        add(size, o);
    }

    /**
     * 添加元素到数组的指定位置
     *
     * @param index 指定位置
     * @param o     元素
     */
    @Override
    public void add(int index, T o) {
        if (o == null) return;
        checkIndexOfAdd(index);
        // likelove: 2020/10/1  动态扩容
        expandCapacity(size + 1);
        for (int i = size; i > index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = o;
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
        T remove = elements[index];
        for (int i = index; i < size; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size] = null;
        size--;
        return remove;
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
        if (o == null) return null;
        int index = indexOf(o);
        return remove(index);
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
        //判断字符是否在正常范围之内
        checkIndex(index);
        return elements[index];
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
        // 检查index是否合法
        checkIndex(index);
        //取出原有的元素
        T old = elements[index];
        elements[index] = o;
        return old;
    }

    /**
     * 清空数组
     */
    @Override
    public void clear() {
        elements = (T[]) new Object[elements.length];
        size = 0;
    }

    /**
     * 动态扩容
     *
     * @param needCapacity 需要的容量
     */
    private void expandCapacity(int needCapacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= needCapacity) {
            return;
        }
        int newCapacity = (oldCapacity >> 1) + oldCapacity;
        Object[] newElements = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = (T[]) newElements;
        System.out.println(oldCapacity + "扩容为 " + newCapacity);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb./*append("size = ").append(size).*/append("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(",").append(" ");
            }
        }
        sb.append("]");
        return String.valueOf(sb);
    }
}
```



# 7.抽取List接口

```java
package 数据结构和算法.数据结构.list;

/**
 * @author likeLove
 * @since 2020-09-29  20:54
 * List的接口设计
 */
public interface List<T> {
    /**
     * 获取数组元素个数
     *
     * @return 当前数组元素个数
     */
    int size();


    /**
     * 判断元素是否存在
     *
     * @param o 元素
     *
     * @return 返回这个元素是否存在
     */
    boolean contains(T o);

    /**
     * 判断数组是否为空
     *
     * @return 数组是否为空
     */
    boolean isEmpty();

    /**
     * 查找元素在数组中的位置
     *
     * @param o 需要查找的元素
     *
     * @return 元素在数组中的索引
     */
    int indexOf(T o);

    /**
     * 添加元素到数组
     *
     * @param o 需要添加的元素
     */
    void add(T o);

    /**
     * 添加元素到数组的指定位置
     *
     * @param index 指定位置
     * @param o     元素
     */
    void add(int index, T o);

    /**
     * 移除指定位置的元素
     *
     * @param index 指定位置
     *
     * @return 被移除的元素
     */
    T remove(int index);

    /**
     * 移除指定元素
     * @param o o
     * @return 被移除的元素
     */
    T remove(T o);

    /**
     * 根据索引返回元素(从0开始）
     *
     * @param index 元素的索引
     *
     * @return 元素
     */
    T get(int index);

    /**
     * 为索引位置设置新的元素
     *
     * @param index 索引
     * @param o     新元素
     *
     * @return 旧元素
     */
    T set(int index, T o);

    /**
     * 清空数组
     */
    void clear();
}
```



# 8.抽取AbstractList抽象类

```java
package 数据结构和算法.数据结构.list;

/**
 * @author likeLove
 * @since 2020-10-01  15:14
 */
public abstract class AbstractList<T> implements List<T> {
    /**
     * 当前数组元素个数
     */
    protected int size;

    /**
     * 获取数组元素个数
     *
     * @return 当前数组元素个数
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 判断元素是否存在
     *
     * @param o 元素
     *
     * @return 返回这个元素是否存在
     */
    @Override
    public boolean contains(T o) {
        //查找这个元素的索引，如果返回的索引 > 0，就表示这个元素不存在
        return indexOf(o) >= 0;
    }

    /**
     * 判断数组是否为空
     *
     * @return 数组是否为空
     */
    @Override
    public boolean isEmpty() {
        //如果数组大小 == 0 就表示数组为空
        return size == 0;
    }

    /**
     * 检查索引是否越界
     *
     * @param index 索引
     */
    protected void checkIndex(int index) {
        if (index < 0 || index >= size) {
            outOfBoundsException(index);
        }
    }

    /**
     * 检查索引是否越界（仅限添加方法）
     *
     * @param index 索引
     */
    protected void checkIndexOfAdd(int index) {
        if (index < 0 || index > size) {
            outOfBoundsException(index);
        }
    }

    /**
     * 抛出数组越界异常
     *
     * @param index 索引
     */
    protected void outOfBoundsException(int index) {
        throw new IndexOutOfBoundsException("index = " + index + "," + "size = " + size);
    }
}
```





# 9.动态缩容

```java
/**
 * Trim capacity. 动态缩容
 * 不需要缩容的情况：
 * 1.实用的空间超过总容量的一半
 * 2.使用的空间小于默认的容量
 * 
 * size：已经使用的容量
 * capacity：总容量
 */
private void trimCapacity() {
    int oldCapacity = elements.length;
    int newCapacity = (oldCapacity >> 1) ;
    if (size >= newCapacity || oldCapacity <= DEFAULT_CAPACITY) {
        return;
    }

    //缩容
    Object[] newElements = new Object[newCapacity ];
    for (int i = 0; i < size; i++) {
        newElements[i] = elements[i];
    }

    //引用
    elements = (T[]) newElements;
    System.out.println(oldCapacity + "缩容 " + newCapacity);
}
```

