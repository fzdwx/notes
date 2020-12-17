package 设计模式.创建者模式.原型模式.浅克隆;

/**
 * @author like
 * @date 2020-12-15 17:01
 * @contactMe 980650920@qq.com
 * @description
 */
public class People implements Cloneable{
    public People() {
        System.out.println("People创建");
    }

    @Override
    public People clone() throws CloneNotSupportedException {
        System.out.println("原型复制");
        return (People) super.clone();
    }
}
