package 设计模式.结构型模式.装饰者模式;

/**
 * @author like
 * @date 2020-12-20 14:39
 * @contactMe 980650920@qq.com
 * @description 具体构建 炒饭
 */
public class FriedRice extends FastFood{

    public FriedRice() {
        super(10,"炒饭");
    }

    @Override
    public float cost() {
        return getPrice();
    }
}
