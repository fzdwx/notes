package 设计模式.行为模式.迭代器模式;

import java.util.List;

/**
 * @author like
 * @date 2021-01-01 16:24
 * @contactMe 980650920@qq.com
 * @description
 */
public class StudentIteratorImpl implements StudentIterator {

    private List<Student> list;
    private int position = 0;

    public StudentIteratorImpl(List<Student> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return position <= list.size();
    }

    @Override
    public Student next() {
        return list.get(position++);
    }
}
