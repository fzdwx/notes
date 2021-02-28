package 算法.排序.tools;

/**
 * @author like
 * @date 2020-12-30 8:25
 * @contactMe 980650920@qq.com
 * @description
 */
public class Student implements Comparable<Student> {
    public int score;
    public int age;

    public Student(int score, int age) {
        this.score = score;
        this.age = age;
    }

    @Override
    public int compareTo(Student o) {
        return age - o.age;
    }
}
