package 设计模式.行为模式.观察者模式;


/**
 * @author like
 * @date 2020-12-30 12:30
 * @contactMe 980650920@qq.com
 * @description 抽象主题角色类
 */
public interface Subject {

    /**
     * 附加
     *
     * @param observer 观察者
     */
    void attach(Observer observer);

    /**
     * 分离
     *
     * @param observer 观察者
     */
    void detach(Observer observer);

    /**
     * 通知订阅者更新消息
     *
     * @param message 消息
     */
    void notify(String message);
}
