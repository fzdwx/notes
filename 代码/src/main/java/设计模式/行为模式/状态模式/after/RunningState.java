package 设计模式.行为模式.状态模式.after;

/**
 * @author like
 * @date 2020-12-29 18:00
 * @contactMe 980650920@qq.com
 * @description
 */
public class RunningState extends LifeState {
    // 电梯运行的时候
    @Override
    public void open() {
        // do nothing
    }

    @Override
    public void close() {
        // do nothing
    }

    @Override
    public void run() {
        System.out.println("电梯正在运行");
    }

    @Override
    public void stop() {
        context.setState(Context.stoppingState).stop();
    }
}
