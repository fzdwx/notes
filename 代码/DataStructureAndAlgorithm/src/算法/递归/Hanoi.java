package 算法.递归;

/**
 * @author like
 * @date 2021-01-14 14:43
 * @contactMe 980650920@qq.com
 * @description
 */
public class Hanoi {

    public static void main(String[] args) {
        hanoi(1, "A", "B", "C");
    }

    public static void hanoi(int n, String a, String center, String c) {
        if (n == 1) {
            move(n, a, c);
            return;
        }
        hanoi(n - 1, a, c, center);
        move(n, a, c);
        hanoi(n - 1, center, a, c);
    }

    private static void move(int n, String from, String to) {
        System.out.println("将" + n + "号盘子从" + from + "移动到" + to);
    }
}
