package 数据结构.list;

/**
 * @author likeLove
 * @since 2020-09-29  20:54
 * List的接口设计
 */
public interface List<T> {
    /**
     * 获取数组元素个数
     *
     * @return 当前数组元素个数
     */
    int size();

    /**
     * 判断元素是否存在
     *
     * @param o 元素
     * @return 返回这个元素是否存在
     */
    boolean contains(T o);

    /**
     * 判断数组是否为空
     *
     * @return 数组是否为空
     */
    boolean isEmpty();

    /**
     * 查找元素在数组中的位置
     *
     * @param o 需要查找的元素
     * @return 元素在数组中的索引
     */
    int indexOf(T o);

    /**
     * 添加元素到数组
     *
     * @param o 需要添加的元素
     */
    void add(T o);

    /**
     * 添加元素到数组的指定位置
     *
     * @param index 指定位置
     * @param o     元素
     */
    void add(int index, T o);

    /**
     * 移除指定位置的元素
     *
     * @param index 指定位置
     * @return 被移除的元素
     */
    T remove(int index);

    /**
     * 移除指定元素
     *
     * @param o o
     * @return 被移除的元素
     */
    T remove(T o);

    /**
     * 根据索引返回元素(从0开始）
     *
     * @param index 元素的索引
     * @return 元素
     */
    T get(int index);

    /**
     * 为索引位置设置新的元素
     *
     * @param index 索引
     * @param o     新元素
     * @return 旧元素
     */
    T set(int index, T o);

    /**
     * 清空数组
     */
    void clear();
}
