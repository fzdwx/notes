package 设计模式.创建者模式.工厂模式.工厂方法模式;

/**
 * @author like
 * @date 2020-12-14 15:49
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        CoffeeStore coffeeStore = new CoffeeStore(new USACoffeeFactory());
        coffeeStore.orderCoffee();
    }
}
