package 设计模式.行为模式.命令模式;

import java.util.Map;

/**
 * @author like
 * @date 2020-12-27 20:15
 * @contactMe 980650920@qq.com
 * @description
 */
public class OrderCommand implements Command {

    private Chef receiver;
    private Order order;

    public OrderCommand(Chef receiver, Order order) {
        this.receiver = receiver;
        this.order = order;
    }

    @Override
    public void execute() {
        System.out.printf("%s桌的订单:\r\n",order.getDiningTable());
        Map<String, Integer> fooDir = order.getFooDir();
        fooDir.forEach((name,num) -> {
            receiver.makeFood(name,num);
        });
        System.out.printf("%s桌的订单完成了\n\r",order.getDiningTable());
    }
}
