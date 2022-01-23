package site.laomst.learn.dsag.adt.tree;

/**
 * 红黑树中的节点，一类被标记为红色，一类被标记为黑色，除此之外，一颗红黑树还需要满足以下几个要求：
 * <ul>
 *     <li>根节点是黑色的</li>
 *     <li>每个叶子节点都是黑色的空节点(NIL)，也就是说，叶子节点是不存储数据的，这个规定主要是为了简化红黑树代码的实现</li>
 *     <li>父节点和子节点不能同时为红色，也就是说，红色节点是被黑色节点隔开的</li>
 *     <li>每个节点，从该节点到达其可达子节点的所有路径，都包含相同数目的黑色节点</li>
 *     <li>新插入的节点的颜色是红色的，而基于二叉查找树的特点，我们又知道，新插入的节点必定是在子节点上的</li>
 * </ul>
 * @param <K>
 * @param <V>
 */
public class RedBlackTreeMap<K, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    static class Node {
        Object key;
        Object value;
        Node left, right;
        boolean color;
        int size;

        public Node(Object key, Object value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    private Node root;

    /**
     * 在插入的过程中，红黑树的第3、4点规则可能会被破坏
     * @param key
     * @param value
     */
    public void put(K key, V value) {

    }


}
