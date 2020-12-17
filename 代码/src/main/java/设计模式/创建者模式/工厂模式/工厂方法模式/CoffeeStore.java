package 设计模式.创建者模式.工厂模式.工厂方法模式;


/**
 * @author like
 * @date 2020-12-14 15:34
 * @contactMe 980650920@qq.com
 * @description
 */
public class CoffeeStore {

    private CoffeeFactory factory;

    public CoffeeStore(CoffeeFactory factory) {
        this.factory = factory;
    }

    public Coffee orderCoffee() {
        return factory.createCoffee().addMilk().addSugar();
    }
}
