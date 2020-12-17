package 设计模式.创建者模式.工厂模式.简单工厂模式;

/**
 * @author like
 * @date 2020-12-14 15:18
 * @contactMe 980650920@qq.com
 * @description 咖啡店
 */
public class SimpleCoffeeFactory {
    public static Coffee createCoffee(String type) {
        Coffee coffee = null;
        switch (type) {
            case "美式咖啡":
                coffee = new USACoffee();
                break;
            case "拿铁咖啡":
                coffee = new LatteCoffee();
                break;
            default:
                throw new RuntimeException("没有找到对应的咖啡");
        }
        return coffee;
    }
}
