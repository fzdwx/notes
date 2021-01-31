package 设计模式.行为模式.中介者模式;

/**
 * @author like
 * @date 2020-12-31 13:24
 * @contactMe 980650920@qq.com
 * @description  具体的同事角色类 房主
 */
public class HostOwner extends Person {

    public HostOwner(String name, Mediator mediator) {
        super(name, mediator);
    }

    @Override
    public void communication(String message) {
        mediator.constanct(message,this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println("房主:"+name+",获取到的信息是:"+message);
    }
}
