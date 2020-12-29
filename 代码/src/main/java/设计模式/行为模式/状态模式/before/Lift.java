package 设计模式.行为模式.状态模式.before;

/**
 * @author like
 * @date 2020-12-29 17:36
 * @contactMe 980650920@qq.com
 * @description
 */
public class Lift implements ILift {

    // 声明一个记录当前电梯状态的变量
    private int state;

    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void open() {
        switch (state) {  // 当前电梯状态
            case OPENING_STATE:
            case RUNNING_STATE:  // 什么都不做
                break;
            case CLOSE_STATE:
            case STOPPING_STATE:
                System.out.println("电梯打开了...");
                setState(OPENING_STATE);
                break;

        }
    }

    @Override
    public void close() {
        switch (state) {  // 当前电梯状态
            case OPENING_STATE:
                System.out.println("电梯关闭了...");
                setState(CLOSE_STATE);
            case RUNNING_STATE:  // 什么都不做
            case CLOSE_STATE:
            case STOPPING_STATE:
                break;

        }
    }

    @Override
    public void run() {
        switch (state) {  // 当前电梯状态
            case OPENING_STATE:
                // do xx
                break;
            case RUNNING_STATE:
                // do xx
                break;
            case CLOSE_STATE:
            case STOPPING_STATE:
                System.out.println("电梯开始运行了...");
                setState(RUNNING_STATE);
                break;

        }
    }

    @Override
    public void stop() {
        switch (state) {  // 当前电梯状态
            case OPENING_STATE:
               // do xx
                break;
            case RUNNING_STATE:  // 什么都不做
            case CLOSE_STATE:
                System.out.println("电梯停止了...");
                setState(RUNNING_STATE);
            case STOPPING_STATE:
                // do xx
                break;
        }
    }
}
