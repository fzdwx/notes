package 设计模式.行为模式.中介者模式;

/**
 * @author like
 * @date 2020-12-31 13:21
 * @contactMe 980650920@qq.com
 * @description 具体的同事角色类
 */
public class Tenant extends Person {

    /**
     * 沟通
     *
     * @param message 消息
     */
    public void communication(String message) {
        mediator.constanct(message,this);
    }

    public void getMessage(String message) {
        System.out.println("租房者:"+name+"获取到的信息是"+message);
    }

    public Tenant(String name, Mediator mediator) {
        super(name, mediator);
    }
}
