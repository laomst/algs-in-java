package site.laomst.learn.dsag.adt.list;

import java.util.Arrays;

public class ArrayList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    // 共享的0容量的数据容器
    private static final Object[] EMPTY_ELEMENTDATA = {};

    // 共享的默认容量数据容器，将它和EMPTY_ELEMENTDATA区分开的目的是在扩容的时候判断是否扩容到默认容量
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private Object[] elementData;

    private int size;

    public ArrayList() {
        elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public ArrayList(int initCapacity) {
        if (initCapacity < 0) {
            throw new IllegalArgumentException("The Array capacity must be greater than 0.");
        } else if (initCapacity == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            elementData = new Object[initCapacity];
        }
    }

    public void add(E e) {
        growCapacityIfNecessary(size + 1);
        elementData[size++] = e;
    }

    public void insert(int index, E e) {
        indexCheckForInsert(index);
        growCapacityIfNecessary(size + 1);
        // 从index开始，原来的元素需要向后移动一位
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = e;
        size++;
    }

    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    public E set(int index, E e) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = e;
        return oldValue;
    }

    public E remove(int index) {
        rangeCheck(index);

        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        }

        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
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
        if (newCapacity > MAX_ARRAY_SIZE) {
            newCapacity = Integer.MAX_VALUE;
        }
        System.out.println("进行了扩容操作 oldCapacity:" + oldCapacity + " newCapacity:" + newCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void indexCheckForInsert(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }


}
