package 设计模式.创建者模式.工厂模式.工厂方法模式;

/**
 * @author like
 * @date 2020-12-14 15:15
 * @contactMe 980650920@qq.com
 * @description
 */
public abstract class Coffee {
    public abstract String getName();

    public Coffee addSugar() {
        System.out.println(getName() + ":加糖");
        return this;
    }

    public Coffee addMilk() {
        System.out.println(getName() + ":加奶");
        return this;
    }
}
