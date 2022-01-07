package site.laomst.learn.dsag.adt.stack;

import java.util.NoSuchElementException;

public class LinkedStack<E> {

    private int size = 0;

    transient private Node<E> top;

    public void push(E e) {
        top = new Node<>(e, top);
        size ++;
    }

    public E pop() {
        if (top == null) {
            throw new NoSuchElementException();
        }
        Node<E> t = top;
        E e = t.item;
        top = top.next;
        t.next = null;
        t.item = null;
        size--;
        return e;
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }
}
