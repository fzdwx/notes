package 设计模式.行为模式.命令模式;

import java.util.ArrayList;
import java.util.List;

/**
 * @author like
 * @date 2020-12-27 20:22
 * @contactMe 980650920@qq.com
 * @description
 */
public class Waiter {

    // 持有多个 命令对象
    private static final List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    // 发去命令的功能
    public void orderUp() {
        System.out.println("镁铝服务员：大厨新订单来了....");
        for (Command command : commands) {
            if (command != null) command.execute();
        }
    }
}
