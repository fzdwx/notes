package 设计模式.行为模式.命令模式;

import java.util.HashMap;
import java.util.Map;

/**
 * @author like
 * @date 2020-12-27 20:09
 * @contactMe 980650920@qq.com
 * @description 订单类
 */
public class Order {

    /** 餐桌号码 **/
    private int diningTable;

    /** 餐品 以及对应的分数 **/
    private Map<String, Integer> fooDir = new HashMap<>();

    public int getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(int diningTable) {
        this.diningTable = diningTable;
    }

    public Map<String, Integer> getFooDir() {
        return fooDir;
    }

    public void setFoo(String name, Integer num) {
        fooDir.put(name, num);
    }
}
