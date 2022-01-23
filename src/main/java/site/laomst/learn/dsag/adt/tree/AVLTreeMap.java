package site.laomst.learn.dsag.adt.tree;

import java.util.Comparator;
import java.util.Objects;

public class AVLTreeMap<K, V> {
    static class Node {
        final Object key;
        Object value;
        int height;
        int size;
        Node left;
        Node right;

        Node(Object key, Object value, int height, int size) {
            this.key = key;
            this.value = value;
            this.height = height;
            this.size = size;
        }
    }

    private static final Comparator<Object> DEFAULT_KEY_COMPARATOR = Comparator.comparingInt(Objects::hashCode);

    private Node root;

    private final Comparator<K> keyComparator;

    public AVLTreeMap(Comparator<K> keyComparator) {
        this.keyComparator = keyComparator;
    }

    @SuppressWarnings({"unchecked"})
    public AVLTreeMap() {
        this.keyComparator = (Comparator<K>) DEFAULT_KEY_COMPARATOR;
    }

    public V get(K key) {
        Node x = get(root, key);
        if (x == null) {
            return null;
        }
        return value(x);
    }

    public V put(K key, V value) {
        Node e = get(root, key);
        if (e != null) {
            V oldValue = value(e);
            e.value = value;
            return oldValue;
        }
        root = put(root, key, value);
        return null;
    }

    public V putIfAbsent(K key, V value) {
        Node e = get(root, key);
        if (e != null) {
            V oldValue = value(e);
            if (oldValue == null) {
                e.value = value;
            }
            return oldValue;
        }
        root = put(root, key, value);
        return null;
    }

    public void remove(K key) {
        Node e = get(root, key);
        if (e == null) {
            return;
        }
        root = delete(root, key);
    }

    /**
     * 从指定的子树中删除给定的节点
     * @param x
     * @param key
     * @return
     */
    private Node delete(Node x, K key) {
        int cmp = keyComparator.compare(key, key(x));
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right  = delete(x.right, key);
        } else {
            if (x.left == null) { // 如果左子节点为空，那么其右子节点为空或者为叶子节点，这个时候直接返回其右子节点就可以了
                return x.right;
            } else if (x.right == null) { // 如果其右子节点为空，那么其左子节点为空或者是叶子节点，这个时候直接返回其左子节点就可以了
                return x.left;
            } else {
                // 如果其左子节点和右子节点都不为空，那么这个时候我们有两个选择
                // 1.把左子树中值最大节点复制到当前子树的根节点，删除左子树中值最大的节点
                // 2.把右子树中值最小的节点复制到当前子树的跟节点，删除右子树中值最小的节点
                // 这里我们选择第二种方式
                Node y = x;
                x = min(y.right);
                x.right = deleteMin(y.right);
                x.left = y.left;
            }
        }
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    private Node deleteMin(Node x) {
        // assert x != null;
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    private Node min(Node x) {
        // assert x != null;
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    private int height(Node x) {
        return x == null ? -1 : x.height;
    }

    private int size(Node x) {
        return x == null ? 0 : x.size;
    }

    @SuppressWarnings("unchecked")
    private K key(Node x) {
        return (K) x.key;
    }

    @SuppressWarnings("unchecked")
    private V value(Node x) {
        return (V) x.value;
    }

    /**
     * 返回子树的平衡因子，平衡因子定义为左子树和右子树的高度差
     * most one.
     *
     * @param x the subtree
     * @return the balance factor of the subtree
     */
    private int balanceFactor(Node x) {
        return height(x.left) - height(x.right);
    }

    private Node get(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = keyComparator.compare(key, key(x));
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x;
        }
    }

    private Node put(Node x, K key, V value) {
        if (x == null) {
            return new Node(key, value, 0, 1);
        }
        int cmp = keyComparator.compare(key, key(x));
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.value = value;
            return x;
        }
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    /**
     * 平衡性维持
     */
    private Node balance(Node x) {
        // L
        if (balanceFactor(x) < -1) {
            if (balanceFactor(x.right) > 0) { // 如果是 LR，先调整成 LL
                x.right = rotateRight(x.right);
            }
            // LL 左旋
            x = rotateLeft(x);
        }
        // R
        else if (balanceFactor(x) > 1) {
            if (balanceFactor(x.left) < 0) { // 如果是 RL，先调整成RR
                x.left = rotateLeft(x.left);
            }
            // RR 右旋
            x = rotateRight(x);
        }
        return x;
    }

    /**
     * 右旋给定的子树
     *
     * @param x 子树
     * @return 右旋后的子树
     */
    private Node rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        y.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    /**
     * 右旋给定的子树
     *
     * @param x 子树
     * @return 右旋后的子树
     */
    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        y.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

}
