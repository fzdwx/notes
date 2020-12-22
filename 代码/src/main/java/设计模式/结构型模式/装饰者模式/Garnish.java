package 设计模式.结构型模式.装饰者模式;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author like
 * @date 2020-12-20 14:43
 * @contactMe 980650920@qq.com
 * @description 抽象装饰
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Garnish extends  FastFood {

    private FastFood fastFood;

    public Garnish(float price, String desc, FastFood fastFood) {
        super(price, desc);
        this.fastFood = fastFood;
    }

    public Garnish(FastFood fastFood) {
        this.fastFood = fastFood;
    }
}
