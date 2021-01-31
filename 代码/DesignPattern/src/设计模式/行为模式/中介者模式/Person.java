package 设计模式.行为模式.中介者模式;

/**
 * @author like
 * @date 2020-12-31 13:20
 * @contactMe 980650920@qq.com
 * @description 抽象同事类
 */
public abstract class Person {

    protected String name;
    protected Mediator mediator;

    public Person(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public abstract void communication(String message);
    public abstract void getMessage(String message);
}
