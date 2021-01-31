package 设计模式.创建者模式.工厂模式.抽象工厂;

/**
 * @author like
 * @date 2020-12-14 16:09
 * @contactMe 980650920@qq.com
 * @description
 */
public interface FoodFactory {
    Coffee createCoffee();

    Dessert createDessert();
}
