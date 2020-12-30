package 设计模式.行为模式.观察者模式;

/**
 * @author like
 * @date 2020-12-30 12:31
 * @contactMe 980650920@qq.com
 * @description 抽象观察者
 */
public interface Observer {

    /**
     * 更新
     *
     * @param message 消息
     */
    void update(String message);
}
