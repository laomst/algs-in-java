package site.laomst.learn.dsag.adt.list;

public class Array<E> {

    private static final int DEFAULT_CAPACITY = 10;

    // 共享的0容量的数据容器
    private static final Object[] EMPTY_ELEMENTDATA = {};

    // 共享的默认容量数据容器，将它和EMPTY_ELEMENTDATA区分开的目的是在扩容的时候判断是否扩容到默认容量
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private Object[] elementData;

    private int size;

    public Array() {
        elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public Array(int initCapacity) {
        if (initCapacity < 0) {
            throw new IllegalArgumentException("The Array capacity must be greater than 0.");
        } else if (initCapacity == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            elementData = new Object[initCapacity];
        }
    }

    public boolean add(E e) {

    }

    private void ensureCapacityInternal(int minCapacity) {

    }

    private int calculateCapacity(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    private void ensureExplicitCapacity(int minCapacity) {
        if (minCapacity > elementData.length) {

        }
    }

    private void growCapacity(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + oldCapacity >> 1;
        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }
        if (newCapacity > MAX_ARRAY_SIZE) {
            newCapacity = hugeCapacity(minCapacity);
        }
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }
}
