package site.laomst.learn.dsag.adt.stack;

import java.util.Arrays;

public class LmArrayStack<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] EMPTY_ELEMENTDATA = {};

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    private static final int MAX_STACK_SIZE = Integer.MAX_VALUE - 8;

    private Object[] elementData;

    private int size;

    public LmArrayStack() {
        elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public LmArrayStack(int initCapacity) {
        if (initCapacity < 0) {
            throw new IllegalArgumentException("The Array capacity must be greater than 0.");
        } else if (initCapacity == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            elementData = new Object[initCapacity];
        }
    }

    public void push(E e) {
        growCapacityIfNecessary(size + 1);
        elementData[size++] = e;
    }

    public E pop() {
        return elementData(size--);
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    // 如果有必要的话进行扩容
    private void growCapacityIfNecessary(int minCapacity) {
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
        if (newCapacity > MAX_STACK_SIZE) {
            newCapacity = Integer.MAX_VALUE;
        }
        System.out.println("进行了扩容操作 oldCapacity:" + oldCapacity + " newCapacity:" + newCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
}
