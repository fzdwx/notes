package 设计模式.行为模式.状态模式.before;

/**
 * @author like
 * @date 2020-12-29 17:43
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        Lift lift = new Lift();

        // 当前是关闭的
        lift.setState(ILift.CLOSE_STATE);
        // 设置开启
        lift.run();
    }
}
