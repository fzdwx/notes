package 设计模式.结构型模式.外观模式;

/**
 * @author like
 * @date 2020-12-22 16:31
 * @contactMe 980650920@qq.com
 * @description
 */
public class SmartAppFacade {

    private Light light;
    private TV tv;
    private AirCondition airCondition;

    public SmartAppFacade() {
        this.light = new Light();
        this.tv = new TV();
        this.airCondition = new AirCondition();
    }

    public void say(String message) {
        if (message.contains("open")) {
            on();
        } else if (message.contains("off")) {
            off();
        }else{
            System.out.println("我还听不懂你说的");
        }
    }

    private void off() {
        light.off();
        tv.off();
        airCondition.off();
    }

    private void on() {
        light.on();
        tv.on();
        airCondition.on();
    }
}
