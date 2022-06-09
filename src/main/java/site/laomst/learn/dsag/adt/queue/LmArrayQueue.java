package site.laomst.learn.dsag.adt.queue;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * 顺序队列，入队的时候，可能会进行清理或者扩容操作
 * @param <E>
 */
public class LmArrayQueue<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] EMPTY_ELEMENTDATA = {};

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    private static final int MAX_QUEUE_SIZE = Integer.MAX_VALUE - 8;

    private int size = 0;
    private int tail = 0;
    private int head = 0;
    private Object[] elementData;

    public LmArrayQueue() {
        elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public LmArrayQueue(int initCapacity) {
        if (initCapacity < 0) {
            throw new IllegalArgumentException("The ArrayQueue capacity must be greater than 0.");
        } else if (initCapacity == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            elementData = new Object[initCapacity];
        }
    }

    public void enqueue(E e) {
        growOrClear();
        elementData[tail++] = e;
        size ++;
    }

    public E dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        size --;
        return elementData(head++);
    }


    // 如果有必要的话进行扩容
    private void growOrClear() {
        // tail表示马上要用的index
        if (tail <= elementData.length-1) {
            return;
        }
        if (head > 0) {
            clearCapacity();
        } else {
            growCapacity(size + 1);
        }
    }

    private void clearCapacity() {
        if (tail == head) {
            tail = head = 0;
        } else {
            System.arraycopy(elementData, head, elementData, 0, size);
            head = 0;
            tail = size;
        }
    }

    private void growCapacity(int minCapacity) {
        // 已经发生溢出 overflow
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        // 如果创建的是默认容量的Array，那么第一次扩容就要扩展到默认容量，否则就按正常的扩容逻辑来
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        if (minCapacity < elementData.length) {
            return;
        }
        int oldCapacity = elementData.length;
        // 正常的扩容逻辑为扩展至原来容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }
        if (newCapacity > MAX_QUEUE_SIZE) {
            newCapacity = Integer.MAX_VALUE;
        }
        System.out.println("进行了扩容操作 oldCapacity:" + oldCapacity + " newCapacity:" + newCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }


    public String printAll() {
        String str = Arrays.toString(Arrays.copyOfRange(elementData, head, tail));
        System.out.println(str);
        return str;
    }
}
