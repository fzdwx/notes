package 设计模式.行为模式.状态模式.after;

/**
 * @author like
 * @date 2020-12-29 18:13
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        // 环境对象
        Context context = new Context();

        context.setState(Context.closeState);

        context.open();
        context.run();
        context.close();
        context.stop();
    }
}
