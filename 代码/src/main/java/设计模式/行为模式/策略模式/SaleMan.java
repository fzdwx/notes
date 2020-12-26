package 设计模式.行为模式.策略模式;

/**
 * @author like
 * @date 2020-12-26 16:33
 * @contactMe 980650920@qq.com
 * @description
 */
public class SaleMan {
    private Strategy strategy;

    public SaleMan(Strategy strategy) {
        this.strategy = strategy;
    }

    public void salesManShow() {
        strategy.show();
    }
}
