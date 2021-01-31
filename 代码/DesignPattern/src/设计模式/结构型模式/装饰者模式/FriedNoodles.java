package 设计模式.结构型模式.装饰者模式;

/**
 * @author like
 * @date 2020-12-20 14:40
 * @contactMe 980650920@qq.com
 * @description 炒面
 */
public class FriedNoodles extends FastFood {

    public FriedNoodles() {
        super(8,"炒面");
    }

    @Override
    public float cost() {
        return getPrice();
    }
}
