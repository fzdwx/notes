package 设计模式.创建者模式.建造者模式;

/**
 * @author like
 * @date 2020-12-16 15:59
 * @contactMe 980650920@qq.com
 * @description
 */
public abstract class Builder {

    protected Bike bike = new Bike();

    public abstract void buildFrame();

    public abstract void buildSeat();

    public abstract Bike createBike();
}
