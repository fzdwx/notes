package 设计模式.创建者模式.工厂模式.工厂方法模式;

/**
 * @author like
 * @date 2020-12-14 15:43
 * @contactMe 980650920@qq.com
 * @description
 */
public interface CoffeeFactory {

    /**
     * 创建咖啡
     *
     * @return {@link Coffee}
     */
     Coffee createCoffee();
}
