package site.laomst.learn.dsag.adt.queue;

import java.util.NoSuchElementException;

public class CircularArrayQueue<E> {
    private Object[] elementData;

    private int head = 0;
    private int tail = 0;
    private int size = 0;

    public CircularArrayQueue(int initCapacity) {
        if (initCapacity < 0) {
            throw new IllegalArgumentException("The CircularArrayQueue capacity must be greater than 0.");
        }
        elementData = new Object[initCapacity];
    }
    
    public void enqueue(E e) {
        if (size == elementData.length) {
            throw new OutOfMemoryError();
        }
        elementData[tail++] = e;
        size++;
        tail %= elementData.length;
    }

    public E dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E oldValue = elementData(head++);
        head %= elementData.length;
        size --;
        return oldValue;
    }


    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }


}
