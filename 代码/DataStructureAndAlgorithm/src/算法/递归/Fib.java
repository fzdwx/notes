package 算法.递归;

import 数据结构.hash.model.Times;

/**
 * @author like
 * @date 2021-01-14 9:23
 * @contactMe 980650920@qq.com
 * @description
 */
public class Fib {

    public static void main(String[] args) {
        int n = 44;
        Times.test("fib1-没有优化", () -> {
            System.out.println(fib1(n));
        });
        Times.test("fib2-使用数组优化", () -> {
            System.out.println(fib2(n));
        });
        Times.test("fib", () -> {
            System.out.println(fib(n));
        });
    }

    public static int fib(int n) {
        if (n <= 2) return 1;
        int f = 1, s = 1;
        for (int i = 3; i <= n; i++) {
            s = f + s;
            f = s - f;
        }
        return s;
    }

    public static int fib1(int n) {
        if (n <= 2) {
            return 1;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

    /** 使用数组优化，避免重复调用 */
    public static int fib2(int n) {
        if (n <= 2) {
            return 1;
        }
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        return fib2(n, array);
    }

    /** 使用滚动数组优化 */
    public static int fib3(int n) {
        if (n <= 2) return 1;
        int[] array = new int[2];
        array[0] = array[1] = 1;
        for (int i = 3; i < n; i++) {
            array[i & 1] = array[(i - 1) & 1] + array[(i - 2) & 1];
        }
        return array[n & 1];
    }

    /** 特征方程 */
    public static int fib4(int n) {
        double c = Math.sqrt(5);
        return (int) ((Math.pow((1 + c) / 2, n) - Math.pow((1 - c) / 2, n)) / c);
    }

    private static int fib2(int n, int[] array) {
        if (array[n] == 0) {
            array[n] = fib2(n - 1, array) + fib2(n - 2, array);
        }
        return array[n];
    }
}
