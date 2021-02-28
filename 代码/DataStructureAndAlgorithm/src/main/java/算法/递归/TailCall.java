package 算法.递归;

/**
 * @author like
 * @date 2021-01-15 9:13
 * @contactMe 980650920@qq.com
 * @description 尾递归
 */
public class TailCall {


    public static int fib(int n) {
        return fib(n, 1, 1);
    }

    /**
     * 求階乘
     */
    public static int a(int n) {
        if (n <= 1) {
            return n;
        }
        return a(n, 1);
    }

    private static int fib(int n, int first, int second) {
        if (n <= 1) {
            return first;
        }
        return fib(n - 1, second, first);
    }

    private static int a(int n, int result) {
        if (n <= 1) {
            return result;
        }
        return a(n - 1, result * n);
    }
}
