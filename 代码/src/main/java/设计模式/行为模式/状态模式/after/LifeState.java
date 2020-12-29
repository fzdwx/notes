package 设计模式.行为模式.状态模式.after;


/**
 * @author like
 * @date 2020-12-29 17:58
 * @contactMe 980650920@qq.com
 * @description
 */
public abstract class LifeState {
    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    // 电梯操作的功能
    public abstract void open();

    public abstract void close();

    public abstract void run();

    public abstract void stop();
}
