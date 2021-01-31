package 设计模式.结构型模式.享元模式;

/**
 * @author like
 * @date 2020-12-24 15:56
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) {
        BoxFactory box = BoxFactory.getInstance();
        AbstractBox i = box.getShape("i");
        i.display("Green");
        AbstractBox i2 = box.getShape("i");
        i2.display("Black");
        System.out.println(i == i2);
    }
}
