package 设计模式.创建者模式.建造者模式;

/**
 * @author like
 * @date 2020-12-16 16:03
 * @contactMe 980650920@qq.com
 * @description
 */
public class Director {

    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Bike build() {
        builder.buildFrame();
        builder.buildSeat();
        return builder.createBike();
    }
}
