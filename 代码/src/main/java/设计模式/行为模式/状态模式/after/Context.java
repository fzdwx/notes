package 设计模式.行为模式.状态模式.after;

/**
 * @author like
 * @date 2020-12-29 17:58
 * @contactMe 980650920@qq.com
 * @description 环境角色
 */
public class Context {

    public final static LifeState openingState = new OpeningState();
    public final static LifeState closeState = new CloseState();
    public final static LifeState runningState = new RunningState();
    public final static LifeState stoppingState = new StoppingState();

    private LifeState state;

    public LifeState getState() {
        return state;
    }

    public Context setState(LifeState state) {
        this.state = state;
        this.state.setContext(this);
        return this;
    }

    public void open() {
        state.open();
    }

    public void close() {
        state.close();
    }

    public void run() {
        state.run();
    }

    public void stop() {
        state.stop();
    }
}
