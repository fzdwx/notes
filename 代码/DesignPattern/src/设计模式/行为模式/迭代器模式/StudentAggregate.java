package 设计模式.行为模式.迭代器模式;

/**
 * @author like
 * @date 2021-01-01 16:28
 * @contactMe 980650920@qq.com
 * @description
 */
public interface StudentAggregate {

    void addStudent(Student student);

    void removeStudent(Student student);

    StudentIterator getIterator();
}
