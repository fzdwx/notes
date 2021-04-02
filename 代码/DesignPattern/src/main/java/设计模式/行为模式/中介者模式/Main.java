package 设计模式.行为模式.中介者模式;

/**
 * @author like
 * @date 2020-12-31 13:29
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        // 中介
        MediatorStructure m = new MediatorStructure();

        // 租房者
        Tenant t = new Tenant("like",m);

        // 房主
        HostOwner h = new HostOwner("老王",m);

        // 中介要知道具体的房主和租房者
        m.setTenant(t);
        m.setHostOwner(h);

        // 租房者租房
        t.communication("我要一室一厅的房子");

        // 主人回话
        h.communication("我没有");
    }
}
