package 设计模式.创建者模式.原型模式.深克隆;

/**
 * @author like
 * @date 2020-12-15 17:23
 * @contactMe 980650920@qq.com
 * @description
 */
public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {

        People p1 = new People();
        Student like = new Student("like");
        p1.setStudent(like);

        p1.show();

        People p2 = p1.clone();
        like.setName("keke");
        p2.show();
    }
}
