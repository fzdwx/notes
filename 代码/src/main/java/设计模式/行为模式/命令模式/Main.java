package 设计模式.行为模式.命令模式;

import java.util.Arrays;

/**
 * @author like
 * @date 2020-12-27 20:26
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) {
        int[] arr = new int[]{
                1, 8, 9, 4, 6, 7, 2, 5, 11, 33
        };
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length - 1; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    private static void test() {
        Order o1 = new Order();
        o1.setDiningTable(1);
        o1.setFoo("鸭脖", 2);
        o1.setFoo("西红柿炒鸡蛋", 1);
        o1.setFoo("粉蒸肉", 1);
        o1.setFoo("可口可乐", 1);
        o1.setFoo("米饭", 1);
        Order o2 = new Order();
        o2.setDiningTable(2);
        o2.setFoo("尖椒肉丝盖饭", 1);
        o2.setFoo("雪碧", 1);

        Chef chef = new Chef();


        OrderCommand cmd1 = new OrderCommand(chef, o1);
        OrderCommand cmd2 = new OrderCommand(chef, o2);

        Waiter waiter = new Waiter();
        waiter.addCommand(cmd1);
        waiter.addCommand(cmd2);

        waiter.orderUp();
    }
}
