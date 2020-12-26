package 设计模式.行为模式.策略模式;

/**
 * @author like
 * @date 2020-12-26 16:31
 * @contactMe 980650920@qq.com
 * @description
 */
public class StrategyA implements Strategy {

    @Override
    public void show() {
        System.out.println("买一送一");
    }
}
