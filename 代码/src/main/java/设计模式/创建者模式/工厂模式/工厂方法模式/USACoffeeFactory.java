package 设计模式.创建者模式.工厂模式.工厂方法模式;

/**
 * @author like
 * @date 2020-12-14 15:46
 * @contactMe 980650920@qq.com
 * @description
 */
public class USACoffeeFactory implements CoffeeFactory{

    @Override
    public Coffee createCoffee() {
        return new USACoffee();
    }
}
