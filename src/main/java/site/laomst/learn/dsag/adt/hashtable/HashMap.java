package site.laomst.learn.dsag.adt.hashtable;

import java.util.Objects;

public class HashMap<K, V> {

    /**
     * 默认的初始容量
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * 最小容量值，如果传入的初始容量过小的的话，前两次的put操作就很有可能会触发扩容操作了，所以这里把最小容量设置成了 4
     */
    static final int MINIMUM_CAPACITY = 1 << 2;

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
     *
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
        return (n <= MINIMUM_CAPACITY) ? MINIMUM_CAPACITY : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(K key) {
            this(hash(key), key, null, null);
        }

        Node(K key, V value) {
            this(hash(key), key, value, null);
        }

        Node(K key, V value, Node<K, V> next) {
            this(hash(key), key, value, next);
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
                HashMapV1.Node<?, ?> x = (HashMapV1.Node<?, ?>) o;
                return Objects.equals(key, x.getKey()) && Objects.equals(value, x.getValue());
            }
            return false;
        }
    }

    Node<K, V>[] table;

    int bucketSize;

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

    @SuppressWarnings({"unchecked"})
    final Node<K, V>[] newTable(int capacity) {
        return (Node<K, V>[]) new Node[capacity];
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent) {
        Node<K, V>[] tab;
        Node<K, V> bucketHeader;
        int tableCapacity, bucketIndex;
        if ((tab = table)==null || (tableCapacity = tab.length) == 0) {
            tableCapacity = (tab = initTable()).length;
        }
        if ((bucketHeader = tab[bucketIndex=(tableCapacity-1) & hash]) == null) { // 如果对应的桶上面还没有存储任何数据
//            tab[i] = newNode(hash, key, value, null);
            tab[bucketIndex] = new Node<>(hash, key, value, null);
            bucketSize++;
        } else { // 对应的桶上面已经存储了数据
            Node<K, V> keyNode;
            K k;
            // 这里先判断 p.hash == hash 和 p,key == key 的原因是 为了支持 null key
            if (bucketHeader.hash == hash && ((k = bucketHeader.key) == key || (key != null && key.equals(k)))) { // 如果第一个节点恰好就是我们要寻找的节点
                keyNode = bucketHeader;
            } else {
                while((keyNode = bucketHeader.next) != null) { // 否则，沿着链寻找需要的节点
                    if (keyNode.hash == hash && ((k = keyNode.key) == key || (key != null && key.equals(k)))) {
                        break;
                    }
                    bucketHeader = bucketHeader.next;
                }
            }
            if (keyNode != null) { // 如果key已经存在Map中了
                V oldValue = keyNode.value;
                if (!onlyIfAbsent || oldValue == null) {
                    keyNode.value = value;
                }
                // 如果是修改老节点的值，不需要 size++,也不需要扩容，直接返回
                return oldValue;
            }

            // 否则key还不存在与Map 中
            bucketHeader.next = new Node<>(hash, key, value, null);
        }
        size++;
        if (bucketSize > threshold) {
            resize();
        }
        return null;
    }

    final Node<K, V>[] initTable() {
        // assert table == null || table.length == 0;
        int initCapacity = DEFAULT_INITIAL_CAPACITY;
        if (threshold != 0) {
            initCapacity = tableSizeFor(threshold);
        }
        threshold = newThreshold(initCapacity);
        return (table = newTable(initCapacity));
    }

    final Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        // assert oldTab != null && oldTab.length >= MINIMUM_CAPACITY;
        int oldCap = table.length;
        if (oldCap > MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        int newCap = oldCap << 1;
        Node<K, V>[] newTab = newTable(newCap);
        table = newTab;
        threshold = newThreshold(newCap);
        for (int i = 0; i < oldCap; i++) {
            Node<K, V> e = oldTab[i];
            if (e == null) { // 如果桶里面没有值，跳过
                continue;
            }
            oldTab[i] = null; //help GC
            if (e.next == null) { // 如果没有发生hash碰撞，直接重哈希就可以了
                newTab[e.hash & (newCap - 1)] = e;
            } else { // 如果发生了hash碰撞
                Node<K,V> loHead = null, loTail = null;
                Node<K,V> hiHead = null, hiTail = null;
                Node<K,V> next;
                do {
                    next = e.next;
                    // 计算桶索引的算法是采用了掩码的算法，而新的容量是旧容量的两倍，计算的时候是左移了一位。
                    // 而扩容之后的掩码和原来的掩码的区别就是 oldCap所代表的那一位变成了1 （这也是为什么hashMap在设计的时候规定容量必须是2的整次幂的原因）
                    // 现在，我们直接用oldCap作为掩码来与hash按位与，为0的，桶的索引就不用变，而不为0(其实不为0的话就是oldCap) 的，桶的索引会增加一个 oldCap
                    if ((e.hash & oldCap) == 0) {
                        if (loTail == null)
                            loHead = e;
                        else
                            loTail.next = e;
                        loTail = e;
                    }
                    else {
                        if (hiTail == null)
                            hiHead = e;
                        else
                            hiTail.next = e;
                        hiTail = e;
                    }
                } while ((e = next) != null);
                if (loTail != null) {
                    loTail.next = null;
                    newTab[i] = loHead;
                }
                if (hiTail != null) {
                    hiTail.next = null;
                    newTab[i + oldCap] = hiHead;
                }
            }
        }
        return newTab;
    }

    final int newThreshold(int newTableCapacity) {
        float ft = (float) newTableCapacity * loadFactor;
        return newTableCapacity < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ? (int) ft : Integer.MAX_VALUE;
    }
}
