package 数据结构和算法.数据结构.stack;

import 数据结构和算法.数据结构.list.List;
import 数据结构和算法.数据结构.list.array.ArrayList;

/**
 * @author like
 * @date 2020-12-13 13:14
 * @contactMe 980650920@qq.com
 * @description 栈
 */
public class Stack<T> {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>(new ArrayList<>());
        stack.push(11);
        stack.push(22);
        stack.push(33);
        stack.push(44);
        while (stack.list.size() > 0) {
            System.out.println(stack.pop());
        }
    }

    private List<T> list = new ArrayList<>();

    public Stack() {
    }

    public Stack(List<T> list) {
        this.list = list;
    }

    public void push(T o) {
        list.add(o);
    }

    public T pop() {
        return list.remove(list.size() - 1);
    }

    public T top() {
        return list.get(list.size() - 1);
    }
}
