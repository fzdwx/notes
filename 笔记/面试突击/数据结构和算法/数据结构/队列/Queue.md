





# 队列

`队列`只能在`头尾两端进行操作`。

`对尾rear`：只能在==尾部添加元素==，一般叫做enQueue，入队。

`对头front`：只能从==头部移除元素==，一般叫做deQueue，出队。

先进先出，First In First OUT，FIFO

![image-20201214095157902](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201214095205.png)





 

## 接口设计

![image-20201214095703900](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201214095704.png)







## 代码实现

```java
public class Queue<T> {

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.enQueue(11);
        queue.enQueue(22);
        queue.enQueue(33);
        queue.enQueue(44);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

    private List<T> list;


    public Queue() {
        list = new LinkedList<>();
    }

    public Queue(List<T> list) {
        this.list = list;
    }

    public Integer size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueue(T o) {
        list.add(o);
    }


    public T deQueue() {
        return list.remove(0);
    }

    public T front() {
        return list.get(0);
    }
}
```



# 力扣-用栈实现队列

```java
public class _用栈实现队列 {

    public static void main(String[] args) {
        MyQueue q = new MyQueue();
        q.push(11);
        q.push(22);
        q.push(33);
        q.push(44);
        while (!q.empty()) {
            System.out.println(q.pop());
        }
        System.out.println(q.empty());
    }

}
class  MyQueue{

    private static final Stack<Integer> S1 = new Stack<>();
    private static final Stack<Integer> S2 = new Stack<>();

    /** Initialize your data structure here. */
    public MyQueue() {

    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        S1.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        checkOutIsEmpty();
        return S2.pop();
    }


    /** Get the front element. */
    public int peek() {
        checkOutIsEmpty();
        return S2.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return S1.isEmpty() && S2.isEmpty();
    }

    private void checkOutIsEmpty() {
        if (S2.isEmpty()) {
            while (!S1.isEmpty()) {
                S2.push(S1.pop());
            }
        }
    }
}
```





# 双端队列Deque

双端队列就是能在==头尾添加删除==的队列

![image-20201214105454143](https://gitee.com/likeloveC/picture_bed/raw/master/img/8.26/20201214105454.png)





## 代码实现

```java
public class Deque<T> {
    private List<T> list;


    public Deque() {
        list = new LinkedList<>();
    }

    public Deque(List<T> list) {
        this.list = list;
    }

    public Integer size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueueRear(T o) {
        list.add(o);
    }

    public void enQueueFront(T o) {
        list.add(0, o);
    }

    public T deQueueFront() {
        return list.remove(0);
    }

    public T deQueueRear() {
        return list.remove(size() - 1);
    }

    public T front() {
        return list.get(0);
    }

    public T rear() {
        return list.get(size() - 1);
    }
}
```





# 循环队列



## 核心-找到指定位置的真实索引

~~~java
  /**
     * 找到i位置的元素的索引
     */
    private int index(int i) {
        i += frontIndex;
        if (i < 0) {
            i += defaultCapacity;
        }
//        return i % defaultCapacity;
        return i - (i >= defaultCapacity ? defaultCapacity : 0);
    }

~~~







## 代码实现-使用数组

```java
public class CircleQueue<T>  {

    public static void main(String[] args) {
        CircleQueue<Integer> queue = new CircleQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }
        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }
        for (int i = 0; i < 20; i++) {
            queue.enQueue(i);
        }
        System.out.println(queue);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

    private T[] element;
    private Integer size = 0;
    private Integer frontIndex = 0;

    private Integer defaultCapacity = 10;

    public CircleQueue() {
        this(10);
    }

    public CircleQueue(Integer capacity) {
        this.defaultCapacity = capacity;
        element = (T[]) new Object[defaultCapacity];
    }

    public Integer size() {
        return size;
    }


    public boolean isEmpty() {
//        return size %defaultCapacity == 0;
        return size == 0;
    }

    public void enQueue(T o) {
        expansion(size + 1);
        element[index(size)] = o;
        size++;
    }

    public T deQueue() {
        T res = element[frontIndex];
        element[frontIndex] = null;
        frontIndex = index(1);
        size--;
        return res;
    }

    private void expansion(int needCapacity) {
        if (defaultCapacity >= needCapacity) {
            return;
        }
        int oldCapacity = element.length;
        int newCapacity = (defaultCapacity >> 1) + defaultCapacity;
        T[] newElement = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElement[i] = element[index(i)];
        }

        element = newElement;
        defaultCapacity = needCapacity;
        frontIndex = 0;

    }

    private int index(int i) {
        i += frontIndex;
        if (i < 0) {
            i += defaultCapacity;
        }
        return i % defaultCapacity;
    }


    public T front() {
        return element[frontIndex];
    }

    @Override
    public String toString() {
        return "CircleQueue{" +
                "element=" + Arrays.toString(element) +
                ", size=" + size +
                ", frontIndex=" + frontIndex +
                ", defaultCapacity=" + defaultCapacity +
                '}';
    }
}
```











```java




public class CircleQueue<T> {

    public static void main(String[] args) {
        CircleQueue<Integer> queue = new CircleQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }
        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }
        for (int i = 0; i < 20; i++) {
            queue.enQueue(i);
        }
        System.out.println(queue);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }

    private T[] element;
    private Integer size = 0;
    private Integer frontIndex = 0;

    private Integer defaultCapacity = 10;

    public CircleQueue() {
        this(10);
    }

    public CircleQueue(Integer capacity) {
        this.defaultCapacity = capacity;
        element = (T[]) new Object[defaultCapacity];
    }

    public Integer size() {
        return size;
    }


    public boolean isEmpty() {
//        return size %defaultCapacity == 0;
        return size == 0;
    }

    public void enQueue(T o) {
        expansion(size + 1);
        element[index(size)] = o;
        size++;
    }

    public T deQueue() {
        T res = element[frontIndex];
        element[frontIndex] = null;
        frontIndex = index(1);
        size--;
        return res;
    }

    private void expansion(int needCapacity) {
        if (defaultCapacity >= needCapacity) {
            return;
        }
        int oldCapacity = element.length;
        int newCapacity = (defaultCapacity >> 1) + defaultCapacity;
        T[] newElement = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElement[i] = element[index(i)];
        }

        element = newElement;
        defaultCapacity = needCapacity;
        frontIndex = 0;

    }

    private int index(int i) {
        return (frontIndex + i) % defaultCapacity;
    }

    public T front() {
        return element[frontIndex];
    }

    @Override
    public String toString() {
        return "CircleQueue{" +
                "element=" + Arrays.toString(element) +
                ", size=" + size +
                ", frontIndex=" + frontIndex +
                ", defaultCapacity=" + defaultCapacity +
                '}';
    }
}
```



# 双端循环队列



```java
public class CircleDeque<T> {


    private T[] element;
    private Integer size = 0;
    private Integer frontIndex = 0;

    private Integer defaultCapacity = 10;

    public CircleDeque() {
        this(10);
    }

    public CircleDeque(Integer capacity) {
        this.defaultCapacity = capacity;
        element = (T[]) new Object[defaultCapacity];
    }

    public Integer size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }


    public void enQueueRear(T o) {
        expansion(size + 1);
        element[index(size)] = o;
        size++;
    }


    public T deQueueFront() {
        T res = element[frontIndex];
        element[frontIndex] = null;
        frontIndex = index(1);

        size--;
        return res;
    }

    public T front() {
        return element[frontIndex];
    }


    public void enQueueFront(T o) {
        expansion(size + 1);

        frontIndex = index(-1);
        size++;

        element[frontIndex] = o;
    }

    public T deQueueRear() {
        int index = index(size - 1);

        T res = element[index];
        element[index] = null;

        size--;
        return res;
    }

    public T rear() {
        return element[index(size - 1)];
    }


    /**
     * 找到i位置的元素的索引
     */
    private int index(int i) {
        i += frontIndex;
        if (i < 0) {
            i += defaultCapacity;
        }
        return i % defaultCapacity;
    }

    private void expansion(int needCapacity) {
        if (defaultCapacity >= needCapacity) {
            return;
        }
        int oldCapacity = element.length;
        int newCapacity = (defaultCapacity >> 1) + defaultCapacity;
        T[] newElement = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElement[i] = element[index(i)];
        }

        element = newElement;
        defaultCapacity = needCapacity;
        frontIndex = 0;
    }
}
```