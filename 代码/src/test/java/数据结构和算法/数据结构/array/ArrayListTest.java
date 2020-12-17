package 数据结构和算法.数据结构.array;


import org.junit.Test;
import 数据结构和算法.数据结构.list.array.ArrayList;

import static org.junit.Assert.*;

public class ArrayListTest {


    ArrayList<Integer> myList = new ArrayList<>();
    java.util.ArrayList<Integer> sysList = new java.util.ArrayList<>();

    @Test
    public void testExpandAndTrim() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        System.out.println("===========");
        for (int i = 0; i < 50; i++) {
            list.remove(0);
        }
        System.out.println(list);
    }

    @Test
    public void testAdd() {
        for (int i = 0; i < 10; i++) {
            myList.add(i);
            sysList.add(i);
        }
        assertEquals(myList.toString(), sysList.toString());
    }

    @Test
    public void testAddOfIndex() {
        for (int i = 0; i < 10; i++) {
            myList.add(i, i + 1);
            sysList.add(i, i + 1);
        }
        assertEquals(myList.toString(), sysList.toString());
    }

    @Test
    public void testRemove() {
        for (int i = 0; i < 9; i++) {
            myList.add(i);
            sysList.add(i);
        }
        for (int i = 0; i < 5; i++) {
            myList.remove(i);
            sysList.remove(i);
        }
        assertEquals(myList.toString(), sysList.toString());
    }

    @Test
    public void testRemoveOfObj() {
        for (int i = 0; i < 9; i++) {
            myList.add(i);
            sysList.add(i);
        }
        for (int i = 1; i <= 8; i++) {
            Integer integer = i;
            myList.remove(integer);
            sysList.remove(integer);
        }
        assertEquals(myList.toString(), sysList.toString());
    }

    @Test
    public void testExpand() {
        for (int i = 0; i < 30; i++) {
            myList.add(i);
        }
        System.out.println(myList);
    }
}
