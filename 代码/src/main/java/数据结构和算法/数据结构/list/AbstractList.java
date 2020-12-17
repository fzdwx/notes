package 数据结构和算法.数据结构.list;

/**
 * @author likeLove
 * @since 2020-10-01  15:14
 */
public abstract class AbstractList<T> implements List<T> {
    /**
     * 当前数组元素个数
     */
    protected int size;

    /**
     * 获取数组元素个数
     *
     * @return 当前数组元素个数
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 判断元素是否存在
     *
     * @param o 元素
     *
     * @return 返回这个元素是否存在
     */
    @Override
    public boolean contains(T o) {
        //查找这个元素的索引，如果返回的索引 > 0，就表示这个元素不存在
        return indexOf(o) >= 0;
    }

    /**
     * 判断数组是否为空
     *
     * @return 数组是否为空
     */
    @Override
    public boolean isEmpty() {
        //如果数组大小 == 0 就表示数组为空
        return size == 0;
    }

    /**
     * 检查索引是否越界
     *
     * @param index 索引
     */
    protected void checkIndex(int index) {
        if (index < 0 || index >= size) {
            outOfBoundsException(index);
        }
    }

    /**
     * 检查索引是否越界（仅限添加方法）
     *
     * @param index 索引
     */
    protected void checkIndexOfAdd(int index) {
        if (index < 0 || index > size) {
            outOfBoundsException(index);
        }
    }

    /**
     * 抛出数组越界异常
     *
     * @param index 索引
     */
    protected void outOfBoundsException(int index) {
        throw new IndexOutOfBoundsException("index = " + index + "," + "size = " + size);
    }
}
