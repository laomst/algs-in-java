package site.laomst.learn.dsag.adt.queue;

import java.util.NoSuchElementException;

public class LinkedQueue<E> {

    private int size = 0;

    private transient Node<E> head;
    private transient Node<E> tail;

    public void enqueue(E e) {
        Node<E> t = tail;
        Node<E> newNode = new Node<>(e, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size ++;
    }

    public E dequeue() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        Node<E> h = head;
        head = h.next;
        E oldValue = h.item;
        h.item = null;
        h.next = null;
        size--;
        return oldValue;
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E e, Node<E> next) {
            this.item = e;
            this.next = next;
        }
    }
}
