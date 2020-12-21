package 设计模式.结构型模式.装饰者模式;

/**
 * @author like
 * @date 2020-12-20 14:46
 * @contactMe 980650920@qq.com
 * @description 具体装饰
 */
public class Bacon extends Garnish{

    public Bacon(FastFood fastFood) {
        super(2,"培根",fastFood);
    }

    @Override
    public float cost() {
        return getPrice()+ getFastFood().cost();
    }

    @Override
    public String getDesc() {
        return super.getDesc()+ getFastFood().getDesc();
    }
}
