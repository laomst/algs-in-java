package site.laomst.learn.dsag;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.adt.hashtable.HashMapV1;
import site.laomst.learn.dsag.adt.list.ArrayList;
import site.laomst.learn.dsag.adt.list.LinkedList;
import site.laomst.learn.dsag.adt.queue.ArrayQueue;
import site.laomst.learn.dsag.adt.queue.CircularArrayQueue;
import site.laomst.learn.dsag.adt.queue.LinkedQueue;
import site.laomst.learn.dsag.adt.skiplist.SkipList;
import site.laomst.learn.dsag.adt.skiplist.WZSkipList;
import site.laomst.learn.dsag.adt.stack.ArrayStack;
import site.laomst.learn.dsag.adt.stack.LinkedStack;
import site.laomst.learn.dsag.adt.tree.AVLTree;

import java.util.*;
import java.util.stream.Stream;

public class Tester {
    static final int MAXIMUM_CAPACITY = 1 << 30;

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

    @Test
    public void arrayStackTest() {
        ArrayStack<Integer> arrayStack = new ArrayStack<>();
        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);
        arrayStack.push(4);
        arrayStack.push(5);
        arrayStack.pop();
        arrayStack.pop();
        arrayStack.pop();
        arrayStack.push(10086);
        arrayStack.pop();
        System.out.println();
    }

    @Test
    public void LinkedStackTest() {
        LinkedStack<Integer> arrayStack = new LinkedStack<>();
        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);
        arrayStack.push(4);
        arrayStack.push(5);
        arrayStack.pop();
        arrayStack.pop();
        arrayStack.pop();
        arrayStack.push(10086);
        arrayStack.pop();
        System.out.println();
    }

    @Test
    public void arrayQueue() {
        ArrayQueue<Integer> arrayStack = new ArrayQueue<>();
        arrayStack.enqueue(1);
        String str1 = arrayStack.printAll();
        arrayStack.enqueue(2);
        String str2 = arrayStack.printAll();
        arrayStack.enqueue(3);
        String str3 = arrayStack.printAll();
        arrayStack.enqueue(4);
        String str4 = arrayStack.printAll();
        arrayStack.enqueue(5);
        String str5 = arrayStack.printAll();
        arrayStack.dequeue();
        String str6 = arrayStack.printAll();
        arrayStack.dequeue();
        String str7 = arrayStack.printAll();
        arrayStack.dequeue();
        String str8 = arrayStack.printAll();
        arrayStack.enqueue(11);
        String str9 = arrayStack.printAll();
        arrayStack.enqueue(12);
        String str10 = arrayStack.printAll();
        arrayStack.enqueue(13);
        String str12 = arrayStack.printAll();
        arrayStack.enqueue(14);
        String str13 = arrayStack.printAll();
        arrayStack.enqueue(15);
        String str14 = arrayStack.printAll();
        arrayStack.enqueue(16);
        String str15 = arrayStack.printAll();
        arrayStack.enqueue(17);
        String str16 = arrayStack.printAll();
        arrayStack.enqueue(18);
        String str17 = arrayStack.printAll();
        arrayStack.enqueue(19);
        String str18 = arrayStack.printAll();
        arrayStack.enqueue(20);
        String str19 = arrayStack.printAll();
        arrayStack.enqueue(21);
        String str20 = arrayStack.printAll();
        arrayStack.dequeue();
        String str21 = arrayStack.printAll();
        System.out.println();
    }

    @Test
    public void linkedQueueTest() {
        LinkedQueue<Integer> linkedQueue = new LinkedQueue<>();
        linkedQueue.enqueue(1);
        linkedQueue.enqueue(2);
        linkedQueue.enqueue(3);
        linkedQueue.enqueue(4);
        linkedQueue.enqueue(5);
        linkedQueue.dequeue();
        linkedQueue.dequeue();
        linkedQueue.dequeue();
        linkedQueue.enqueue(6);
        linkedQueue.enqueue(7);
        linkedQueue.dequeue();
    }

    @Test
    public void circularArrayQueueTest() {
        CircularArrayQueue<Integer> arrayQueue = new CircularArrayQueue<>(10);
        arrayQueue.enqueue(1);
        arrayQueue.enqueue(2);
        arrayQueue.enqueue(3);
        arrayQueue.enqueue(4);
        arrayQueue.enqueue(5);
        arrayQueue.enqueue(6);
        arrayQueue.enqueue(7);
        arrayQueue.enqueue(8);
        arrayQueue.enqueue(9);
        arrayQueue.enqueue(10);
        arrayQueue.dequeue();
        arrayQueue.dequeue();
        arrayQueue.enqueue(11);
        arrayQueue.enqueue(12);
        arrayQueue.enqueue(13);
        arrayQueue.enqueue(14);
        arrayQueue.enqueue(15);
        arrayQueue.enqueue(16);
        arrayQueue.enqueue(17);
        arrayQueue.enqueue(18);
        arrayQueue.enqueue(19);
        arrayQueue.enqueue(20);
    }

    @Test
    public void wzSkipListTest() {
        WZSkipList skipList = new WZSkipList();
        skipList.insert(1);
        skipList.insert(2);
        skipList.insert(3);
        skipList.insert(-1);
        skipList.insert(4);
        skipList.insert(6);
        skipList.insert(5);
    }

    @Test
    public void skipListTest() {
        SkipList<Integer> skipList = new SkipList<>(Integer::compareTo);
        skipList.insert(1);
        skipList.insert(2);
        skipList.insert(3);
        skipList.insert(-1);
        skipList.insert(4);
        skipList.insert(6);
        skipList.insert(5);
        skipList.printAll();
    }

    @Test
    public void streamTest() {
        Stream.iterate(1, it -> it + 1)
                .limit(100)
                .filter(it -> it.compareTo(6) > 0)
                .filter(it -> it.compareTo(100) < 0)
                .filter(it -> it % 2 == 1)
                .forEach(System.out::println);
    }

    @Test
    public void HashMapTest() {
        Map<String, String> map = new HashMap<>();
        map.put(null, null);
        map.put("1", "1");
        map.put("2", "2");
        Set<String> strings = map.keySet();
        strings.remove("1");
        System.out.println();
    }


    @Test
    public void to2Test() {
        int cap = 1;
        int n = cap - 1;
//        int n = cap;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println(n);
        n = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        System.out.println(n);
    }

    @Test
    public void SetTest() {
        HashSet<String> set = new HashSet<>();
        set.add(null);
        System.out.println();
    }

    @Test
    public void expressTest() {
        int n;
        int res = (n = 2) + (n >> 1);
        System.out.println(res);
    }

    @Test
    public void HashMapV1Test() {
        HashMapV1<String, String> hashMap = new HashMapV1<>(1);
        hashMap.put("1", "1");
        hashMap.put("2", "1");
        hashMap.put("3", "1");
        hashMap.put("4", "1");
        hashMap.put("5", "1");
        hashMap.put("6", "1");
        hashMap.put("7", "1");
        hashMap.put("8", "1");
        hashMap.put("9", "1");
        hashMap.put("10", "1");
        hashMap.put("a", "1");
        hashMap.put("b", "1");
        hashMap.put("c", "1");
        hashMap.put("d", "1");
        hashMap.put("e", "1");
        hashMap.put("f", "1");
        hashMap.put("g", "1");
        hashMap.put("h", "1");
        hashMap.put("i", "1");
        hashMap.put("j", "1");
        hashMap.put("k", "1");
        hashMap.put("l", "1");
        hashMap.put("m", "1");
        System.out.println();
    }

}
