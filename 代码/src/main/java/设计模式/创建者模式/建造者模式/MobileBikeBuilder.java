package 设计模式.创建者模式.建造者模式;

/**
 * @author like
 * @date 2020-12-16 16:01
 * @contactMe 980650920@qq.com
 * @description
 */
public class MobileBikeBuilder extends Builder{

    @Override
    public void buildFrame() {
        bike.setFrame("Mobile frame");
    }

    @Override
    public void buildSeat() {
        bike.setSeat("Mobile seat");
    }

    @Override
    public Bike createBike() {
        return bike;
    }
}
