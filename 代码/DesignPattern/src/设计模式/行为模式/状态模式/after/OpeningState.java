package 设计模式.行为模式.状态模式.after;

/**
 * @author like
 * @date 2020-12-29 17:59
 * @contactMe 980650920@qq.com
 * @description
 */
public class OpeningState extends LifeState {
    // 电梯开门
    @Override
    public void open() {
        System.out.println("电梯开门...");
    }

    @Override
    public void close() {
        context.setState(Context.closeState).close();
    }

    @Override
    public void run() {
        // 什么都不做
    }

    @Override
    public void stop() {
        // 什么都不做
    }
}
