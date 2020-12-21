package 设计模式.结构型模式.装饰者模式;

import lombok.Data;

/**
 * @author like
 * @date 2020-12-20 14:37
 * @contactMe 980650920@qq.com
 * @description 抽象构建-快餐类
 */
@Data
public abstract class FastFood {

    private float price;
    private String desc;

    public abstract float cost();

    public FastFood() {
    }

    public FastFood(float price, String desc) {
        this.price = price;
        this.desc = desc;
    }
}
