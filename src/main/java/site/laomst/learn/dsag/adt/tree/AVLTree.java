package site.laomst.learn.dsag.adt.tree;

import java.util.Comparator;
import java.util.Objects;

public class AVLTree<K, V> {
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

    public AVLTree(Comparator<K> keyComparator) {
        this.keyComparator = keyComparator;
    }

    @SuppressWarnings({"unchecked"})
    public AVLTree() {
        this.keyComparator = (Comparator<K>) DEFAULT_KEY_COMPARATOR;
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
