package 设计模式.行为模式.观察者模式;

/**
 * @author like
 * @date 2020-12-30 12:39
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        // 1.创建公众号对象
        SubscriptionSubject subject = new SubscriptionSubject();

        // 2.用户订阅公众号
        subject.attach(new WeiXinUser("孙悟空"));
        subject.attach(new WeiXinUser("猪悟能"));
        subject.attach(new WeiXinUser("沙悟净"));

        // 3.公众号发送消息
        subject.notify("唐僧来了");
    }
}
