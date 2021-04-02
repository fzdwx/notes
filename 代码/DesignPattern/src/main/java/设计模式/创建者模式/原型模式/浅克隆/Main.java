package 设计模式.创建者模式.原型模式.浅克隆;

/**
 * @author like
 * @date 2020-12-15 17:03
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        People p1 = new People();
        People p2 = p1.clone();

        System.out.println(p1 == p2);
        System.out.println(p1);
        System.out.println(p2);
    }
}
