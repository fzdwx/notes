package 刷题.队列;

import java.util.Stack;

/**
 * @author like
 * @date 2020-12-14 10:05
 * @contactMe 980650920@qq.com
 * @description https://leetcode-cn.com/problems/implement-queue-using-stacks/
 */
public class _用栈实现队列 {

    public static void main(String[] args) {
        MyQueue q = new MyQueue();
        q.push(11);
        q.push(22);
        q.push(33);
        q.push(44);
        while (!q.empty()) {
            System.out.println(q.pop());
            System.out.println(q.empty());
        }
        System.out.println(q.empty());
    }

}

class MyQueue {

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
