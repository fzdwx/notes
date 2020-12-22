package 设计模式.结构型模式.外观模式;

/**
 * @author like
 * @date 2020-12-22 16:35
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) {
        SmartAppFacade app = new SmartAppFacade();
        app.say("open");

        System.out.println("===   一天过去了     ===");

        app.say("off");
    }
}
