package 设计模式.创建者模式.原型模式.深克隆;

/**
 * @author like
 * @date 2020-12-15 17:20
 * @contactMe 980650920@qq.com
 * @description
 */
public class Student implements Cloneable{
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Student clone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }
}
