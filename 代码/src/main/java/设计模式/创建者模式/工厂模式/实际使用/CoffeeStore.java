package 设计模式.创建者模式.工厂模式.实际使用;

/**
 * @author like
 * @date 2020-12-14 15:34
 * @contactMe 980650920@qq.com
 * @description
 */
public class CoffeeStore {

    public Coffee orderCoffee() {

        Coffee coff = SimpleCoffeeFactory.createCoffee("美式咖啡");

        return coff.addSugar().addMilk();
    }
}
