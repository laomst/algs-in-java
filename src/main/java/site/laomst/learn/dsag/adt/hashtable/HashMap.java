package site.laomst.learn.dsag.adt.hashtable;

import java.util.Objects;

public class HashMap<K, V> {

    /**
     * 默认的初始容量
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * 最大容量，因为HashMap规定桶数组的容量必须是2的整次幂，而整数最大值是(2 << 32) - 1， 所以最大容量规定为 2<< 30，留下一次作为缓冲
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 默认装载因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75F;

    static final int hash(Object key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        hash ^= hash >>> 16;
        return hash;
    }

    /**
     * 返回最小的大于等于给定 cap 的2的整次幂数值
     * @param cap
     * @return
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;  // 因为 int 是 32 位 的，所以经过这几次之后， cap中为1的最高位之下的所有的位就都是1了
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(K key) {
            this(hash(key), key, null, null);
        }

        Node(int hash, K key) {
            this(hash, key, null, null);
        }

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof HashMapV1.Node) {
                HashMapV1.Node<?,?> x = (HashMapV1.Node<?,?>)o;
                return Objects.equals(key, x.getKey()) && Objects.equals(value, x.getValue());
            }
            return false;
        }
    }

    int size;

    int threshold;

    final float loadFactor;

    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }
}
