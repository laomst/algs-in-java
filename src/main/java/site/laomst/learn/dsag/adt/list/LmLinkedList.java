package site.laomst.learn.dsag.adt.list;

public class LmLinkedList<E> {

    private int size = 0;

    transient private Node<E> first;
    transient private Node<E> last;

    public void add(E e) {
        linkLast(e);
    }

    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    public E set(int index, E e) {
        checkElementIndex(index);
        Node<E> node = node(index);
        E oldValue = node.item;
        node.item = e;
        return oldValue;
    }

    public void insert(int index, E e) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(e);
        } else {
            linkBefore(e, node(index));
        }
    }

    public E remove(int index) {
        checkElementIndex(index);
        return unLink(node(index));
    }

    void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    void linkBefore(E e, Node<E> x) {
        final Node<E> pred = x.prev;
        final Node<E> newNode = new Node<>(pred, e, x);
        x.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    void linkAfter(E e, Node<E> x) {
        final Node<E> next = x.next;
        final Node<E> newNode = new Node<>(x, e, next);
        x.next = newNode;
        if (next == null) {
            last = newNode;
        } else {
            next.prev = newNode;
        }
        size++;
    }

    E unLink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> prev = x.prev;
        final Node<E> next = x.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        return element;
    }

    private E unLinkFirst(Node<E> f) {
        // assert f == first && f != null;
        final E element = f.item;
        final Node<E> next = f.next;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        f.item = null;
        f.next = null;
        return element;
    }

    private E unlinkLast(Node<E> l) {
        // assert l == last && l != null;
        final E element = l.item;
        final Node<E> prev = l.prev;
        last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        l.item = null;
        l.prev = null; // help GC
        return element;
    }


    Node<E> node(int index) {
        // assert isElementIndex(index);
        // 如果index在左半区，从前往后，否则就从后往前，这样最坏情况时间复杂度减半
        Node<E> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    // 说明给定的索引是否是现存元素的索引
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    // 说明实参是否为迭代器或添加操作的有效位置的索引。
    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
