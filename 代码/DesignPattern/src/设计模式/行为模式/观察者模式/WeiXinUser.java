package 设计模式.行为模式.观察者模式;

/**
 * @author like
 * @date 2020-12-30 12:38
 * @contactMe 980650920@qq.com
 * @description 具体的观察者角色
 */
public class WeiXinUser implements Observer {
    private String name;

    public WeiXinUser(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + "收到消息：" + message);
    }
}
