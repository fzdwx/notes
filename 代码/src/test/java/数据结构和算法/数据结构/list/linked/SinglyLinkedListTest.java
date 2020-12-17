package 数据结构和算法.数据结构.list.linked;

import org.junit.Test;
import 数据结构和算法.数据结构.list.List;

import static org.junit.Assert.assertEquals;

public class SinglyLinkedListTest {
    List<Integer> list = new SinglyLinkedList<>();
    java.util.List<Integer> sysList = new java.util.LinkedList<>();

    {
        for (int i = 1; i <= 10; i++) {
            list.add(i);
            sysList.add(i);
        }
        System.out.println(list);
        System.out.println(sysList);
    }

    @Test
    public void testIndexOf() {
        assertEquals(list.indexOf(1), 0);
        assertEquals(list.indexOf(2), 1);
        assertEquals(list.indexOf(3), 2);
    }

    @Test
    public void testAdd() {
        sysList.add(10);
        list.add(10);
        assertEquals(sysList.toString(), list.toString());
    }

    @Test
    public void testTestAdd() {
        Integer integer = 10;
        list.add(1, integer);
        sysList.add(1, integer);
        assertEquals(sysList.toString(), list.toString());
    }

    @Test
    public void testRemove() {
        list.remove(1);
        sysList.remove(1);
        assertEquals(sysList.toString(), list.toString());
    }


    @Test
    public void testGet() {
        Integer integer1 = list.get(1);
        Integer integer = sysList.get(1);
        assertEquals(integer1, integer);
    }

    @Test
    public void testSet() {
        Integer integer = 18;
        list.set(1,integer);
        sysList.set(1,integer);
        assertEquals(sysList.toString(), list.toString());
    }

    @Test
    public void testClear() {
        list.clear();
        sysList.clear();
        assertEquals(sysList.toString(), list.toString());
        System.out.println(list);
        System.out.println(sysList);
    }

    @Test
    public void testTestToString() {
        Integer remove = list.remove(0);
        System.out.println(remove);
    }
}
