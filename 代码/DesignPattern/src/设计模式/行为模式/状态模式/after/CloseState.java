package 设计模式.行为模式.状态模式.after;

/**
 * @author like
 * @date 2020-12-29 18:00
 * @contactMe 980650920@qq.com
 * @description
 */
public class CloseState extends LifeState {

    @Override
    public void open() {
        context.setState(Context.openingState).open();
    }

    @Override
    public void close() {
        System.out.println("电梯关门了...");
    }

    @Override
    public void run() {
        context.setState(Context.runningState).run();
    }

    @Override
    public void stop() {
        context.setState(Context.stoppingState).stop();
    }
}
