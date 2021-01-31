package 设计模式.行为模式.状态模式.before;

/**
 * @author like
 * @date 2020-12-29 17:34
 * @contactMe 980650920@qq.com
 * @description 电梯接口
 */
public interface ILift {
    int OPENING_STATE = 1;
    int CLOSE_STATE = 2;
    int RUNNING_STATE = 3;
    int STOPPING_STATE = 4;

    // 设置电梯状态的功能
    void setState(int state);

    // 电梯操作的功能
    void open();

    void close();

    void run();

    void stop();
}
