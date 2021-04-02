package 设计模式.结构型模式.装饰者模式;

/**
 * @author like
 * @date 2020-12-20 14:51
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        FastFood rice = new FriedRice();
        System.out.println(rice.getDesc() + "的价格：" + rice.cost() + "元");
        System.out.println("=====================");

        rice = new Egg(rice);
        System.out.println(rice.getDesc() + "的价格：" + rice.cost() + "元");
        System.out.println("=====================");

        rice = new Bacon(rice);
        System.out.println(rice.getDesc() + "的价格：" + rice.cost() + "元");
    }
}
