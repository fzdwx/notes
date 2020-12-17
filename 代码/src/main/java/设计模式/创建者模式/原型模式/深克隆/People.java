package 设计模式.创建者模式.原型模式.深克隆;

/**
 * @author like
 * @date 2020-12-15 17:01
 * @contactMe 980650920@qq.com
 * @description
 */
public class People implements Cloneable {

    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public void show() {
        System.out.println(student.getName());
    }

    public People() {
        System.out.println("People创建");
    }

    @Override
    public People clone() throws CloneNotSupportedException {
        People p = (People) super.clone();
        p.student = student.clone();
        return p;
    }
}
