package site.laomst.learn.dsag;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.adt.list.ArrayList;
import site.laomst.learn.dsag.adt.list.LinkedList;

public class Tester {

    @Test
    public void arrayTest() {
        ArrayList<String> stringArray = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringArray.add(i + "");
        }
        stringArray.add("123");
        stringArray.remove(2);
        String index2Str = stringArray.get(2);
        System.out.println(index2Str);
        stringArray.insert(2, "2");
        System.out.println();
    }

    @Test
    public void arrayCopyTest() {
//        int[] arr1 = {1, 2, 3, 4, 5};
//        int[] arr2 = {10, 20, 30, 40, 50};
//        System.arraycopy(arr1, 1, arr2, 3, 1);
//        System.out.println();
        int length = (int) 1E8;
        System.out.println(length);
        int[] source = new int[length];
        for (int i = 0; i < length; i++) {
            source[i] = i;
        }
        int[] target1 = new int[length];
        int[] target2 = new int[length];
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < length; i++) {
            target1[i] = source[i];
        }
        System.out.println(System.currentTimeMillis() - start1);
        long start2 = System.currentTimeMillis();
        System.arraycopy(source, 0, target2, 0, length);
        System.out.println(System.currentTimeMillis() - start2);
        System.out.println();
    }

    @Test
    public void linkedListTest() {
        LinkedList<String> list = new LinkedList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        System.out.println(list.get(4));
    }


}
