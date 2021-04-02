package 设计模式.行为模式.状态模式.after;

/**
 * @author like
 * @date 2020-12-29 18:00
 * @contactMe 980650920@qq.com
 * @description
 */
public class StoppingState extends LifeState {
    // 电梯停止了

    @Override
    public void open() {
        context.setState(Context.runningState).run();
    }

    @Override
    public void close() {
        context.setState(Context.closeState).close();
    }

    @Override
    public void run() {
        context.setState(Context.runningState).run();
    }

    @Override
    public void stop() {
        System.out.println("电梯停止了");
    }
}
