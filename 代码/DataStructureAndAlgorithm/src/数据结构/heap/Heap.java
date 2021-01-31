package 数据结构.heap;

/**
 * @author like
 * @date 2020-12-27 8:59
 * @contactMe 980650920@qq.com
 * @description 堆
 */
public interface Heap<E> {
    /**
     * 大小
     *
     * @return int
     */
    int size();


    /**
     * 是空的
     *
     * @return boolean
     */
    boolean isEmpty();

    /**
     * 清晰的
     */
    void clear();

    /**
     * 添加
     *
     * @param element 元素
     */
    void add(E element);

    /**
     * 获得堆顶元素
     *
     * @return {@link E}
     */
    E get();

    /**
     * 删除堆顶元素
     *
     * @return {@link E}
     */
    E remove();

    /**
     * 删除堆顶元素的同时插入一个新元素
     *
     * @param element 元素
     * @return {@link E}
     */
    E replace(E element);
}
