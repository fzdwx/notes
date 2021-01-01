package 设计模式.行为模式.迭代器模式;

import java.util.ArrayList;
import java.util.List;

/**
 * @author like
 * @date 2021-01-01 16:28
 * @contactMe 980650920@qq.com
 * @description
 */
public class StudentAggregateImpl implements StudentAggregate {
    private List<Student> list = new ArrayList<>();

    @Override
    public void addStudent(Student student) {
        list.add(student);
    }

    @Override
    public void removeStudent(Student student) {
        list.remove(student);
    }

    @Override
    public StudentIterator getIterator() {
        return new StudentIteratorImpl(list);
    }
}
