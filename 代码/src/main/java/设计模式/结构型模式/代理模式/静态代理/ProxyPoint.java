package 设计模式.结构型模式.代理模式.静态代理;

/**
 * @author like
 * @date 2020-12-17 17:12
 * @contactMe 980650920@qq.com
 * @description 代理
 */
public class ProxyPoint implements SellTickets{

    private  TrainStation trainStation = new TrainStation();
    @Override
    public void sell() {
        System.out.println("代理收取服务费");
        trainStation.sell();
    }
}
