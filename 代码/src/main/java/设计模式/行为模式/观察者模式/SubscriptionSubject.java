package 设计模式.行为模式.观察者模式;

import java.util.ArrayList;
import java.util.List;

/**
 * @author like
 * @date 2020-12-30 12:35
 * @contactMe 980650920@qq.com
 * @description 订阅主题
 */
public class SubscriptionSubject implements Subject {
    // 定义一个集合，用来存储多个观察者对象
    private List<Observer> weiXinUserList = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        weiXinUserList.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        weiXinUserList.remove(observer);
    }

    @Override
    public void notify(String message) {
        for (Observer observer : weiXinUserList) {
            observer.update(message);
        }
    }
}
