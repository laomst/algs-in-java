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

    @SuppressWarnings("unchecked")
    private K key(Node x) {
        return (K) x.key;
    }

    @SuppressWarnings("unchecked")
    private V value(Node x) {
        return (V) x.value;
    }

    /**
     * Returns the balance factor of the subtree. The balance factor is defined
     * as the difference in height of the left subtree and right subtree, in
     * this order. Therefore, a subtree with a balance factor of -1, 0 or 1 has
     * the AVL property since the heights of the two child subtrees differ by at
     * most one.
     *
     * @param x the subtree
     * @return the balance factor of the subtree
     */
    private int balanceFactor(Node x) {
        return height(x.left) - height(x.right);
    }

    /**
     * Restores the AVL tree property of the subtree.
     *
     * @param x the subtree
     * @return the subtree with restored AVL property
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
     * Rotates the given subtree to the right.
     *
     * @param x the subtree
     * @return the right rotated subtree
     */
    private Node rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        y.size = x.size;
//        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    /**
     * Rotates the given subtree to the left.
     *
     * @param x the subtree
     * @return the left rotated subtree
     */
    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        y.size = x.size;
//        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

}
