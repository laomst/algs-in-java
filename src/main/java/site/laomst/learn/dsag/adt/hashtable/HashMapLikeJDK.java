package site.laomst.learn.dsag.adt.hashtable;

import java.util.Objects;

/**
 * @see java.util.HashMap 的模仿实现，解决桶冲突的策略只是单纯的拉链法，而不会把拉链转换为树
 * @param <K>
 * @param <V>
 */
public class HashMapLikeJDK<K, V> {

    /**
     * 默认的初始容量，必须是2的整次幂
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * 最大容量
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 默认装载因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75F;

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key) {
            this(hash, key, null, null);
        }

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
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
            if (o instanceof Node) {
                Node<?,?> x = (Node<?,?>)o;
                return Objects.equals(key, x.getKey()) && Objects.equals(value, x.getValue());
            }
            return false;
        }
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

    Node<K, V>[] table;

    int size;

    /**
     * 当size达到threshold的时候，表数组就需要进行扩容了，它的值应该是 table.size * loadFactor
     * 但是，当表数组还没有被分配的时候，它保存的是初始容量
     */
    int threshold;

    final float loadFactor;

    public HashMapLikeJDK(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    public HashMapLikeJDK(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMapLikeJDK() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value, false);
    }

    public V putIfAbsent(K key, V value) {
        return putVal(hash(key), key, value, true);
    }

    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    public V getOrDefault(Object key, V defaultValue) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? defaultValue : e.value;
    }

    final Node<K, V> getNode(int hash, Object key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;
        if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n-1) & hash]) != null) {
            if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k)))) {
                return first;
            }
            if ((e = first.next) != null) {
                do {
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                        return e;
                    }
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;
        // 是否需要对table进行初始化
        if ((tab = table) == null || (n = tab.length) == 0) {
            n = (tab = resize()).length;
        }
        if ((p = tab[i=(n-1) & hash]) == null) { // 如果对应的桶上面还没有存储任何数据
//            tab[i] = newNode(hash, key, value, null);
            tab[i] = new Node<>(hash, key, value, null);
        } else { // 对应的桶上面已经存储了数据
            Node<K, V> e;
            K k;
            // 这里先判断 p.hash == hash 和 p,key == key 的原因是 为了支持 null key
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) { // 如果第一个节点恰好就是我们要寻找的节点
                e = p;
            } else {
                while((e = p.next) != null) { // 否则，沿着链寻找需要的节点
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                        break;
                    }
                    p = p.next;
                }
            }
            if (e != null) { // 如果key已经存在Map中了
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null) {
                    e.value = value;
                }
                return oldValue;
            }

            // 否则key还不存在与Map 中
            p.next = newNode(hash, key, value, null);
        }
        if (++size > threshold) {
            resize();
        }
        return null;
    }

    private Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) { // 如果表数组已经被分配过
            if (oldCap > MAXIMUM_CAPACITY) { // 如果桶的数量已经到了最大值，那么之后就不会再触发扩容了
                threshold = Integer.MAX_VALUE;
                return oldTab;
                // 容量翻倍，
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY) {
                // 为什么在老容量是16的时候才会尝试直接翻倍 threshold ？
                // DEFAULT_INITIAL_CAPACITY 也就是16是第一个2的整次幂中第一个大于10的数值，当大于10之后，在乘以一个小数(负载因子一般大于0.1)，这个时候误差就小很多了
                newThr = oldThr << 1;
            }
        }else if (oldThr > 0) { // 如果表数组还没有被分配，并且创建的时候传入了初始容量，那么这个时候初始容量已经在创建实例的时候被计算并保存到 threshold 中了，这是时候直接使用初始容量就可以了
            newCap = oldThr; // 这个时候不会计算 threshold的值
        } else { // 如果创建实例的时候没有传入初始容量，那么初始容量就是 DEFAULT_INITIAL_CAPACITY
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        // 如果 newThr没有被计算出来，那么有以下几种情况：
        // 1、 本次扩容是在初始化表数组，并且我们在构造实例的时候传入了初始容量，这个时候，threshold 中存储的就是我们传入的初始容量，而且这个时候 threshold 的值并没有被计算
        // 2、 第二中情况就是，我们传入的初始容量比较小，经过扩容之后，容量还是小于 DEFAULT_INITIAL_CAPACITY，这个时候上面扩容的时候不会尝试对 threshold 进行翻倍
        // 3、 还有一种情况比较极端，那就是我们设置的负载因子比较小，即使是老容量已经大于等于 DEFAULT_INITIAL_CAPACITY，在上面也尝试对 threshold进行翻倍操作了，但是由于负载因子太小了，翻倍之后值仍然是0，所以这个时候也需要重新计算threshold
        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ? (int)ft : Integer.MAX_VALUE;
        }
        threshold = newThr;
        @SuppressWarnings({"unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null) // 如果没有发生哈希碰撞，直接重哈希就可以了
                        newTab[e.hash & (newCap - 1)] = e;
                    else { // 如果发生了hash碰撞，那么这个时候其实是分两种情况
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
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
        return new Node<>(hash, key, value, next);
    }
}
