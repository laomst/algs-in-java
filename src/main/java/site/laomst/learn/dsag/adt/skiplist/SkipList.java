package site.laomst.learn.dsag.adt.skiplist;

import java.util.Comparator;

public class SkipList<E> {
    private static final float SKIPLIST_P = 0.5f;
    private static final int MAX_LEVEL = 16;

    private int levelCount = 1;

    private Node head = new Node(null); // 头结点中的nexts中保存所有级别的索引的head

    private final Comparator<E> itemComparator;

    public SkipList(Comparator<E> itemComparator) {
        if (itemComparator == null) {
            throw new NullPointerException("元素比较器不能为空");
        }
        this.itemComparator = itemComparator;
    }

    public void insert(E e) {
        int level = randomLevel(); // 随机函数随机出一个索引级别来
        Node newNode = new Node(e);
        newNode.maxLevel = level;
        Node[] update = new Node[level];
        for (int i = 0; i < level; i++) {
            update[i] = head;
        }

        Node p = head;
        // 找到每一级的索引需要插入的位置，存放到update[]中
        for (int i = level-1; i >= 0; i--) {
            while (p.nexts[i] != null && itemComparator.compare(item(p.nexts[i]), e) < 0) {
                p = p.nexts[i];
            }
            update[i] = p;
        }

        // 插入，看单个就是 node.next = target.next; target.next = newNode
        for (int i = 0; i < level; i++) {
            newNode.nexts[i] = update[i].nexts[i];
            update[i].nexts[i] = newNode;
        }

        // 更新整个跳表的索引级别
        if (levelCount < level) {
            levelCount = level;
        }
    }

    public void printAll() {
        Node p = head;
        while (p.nexts[0] != null) {
            System.out.print(p.nexts[0] + " ");
            p = p.nexts[0];
        }
        System.out.println();
    }

    // 理论来讲，一级索引中元素个数应该占原始数据的 50%，二级索引中元素个数占 25%，三级索引12.5% ，一直到最顶层。
    // 因为这里每一层的晋升概率是 50%。对于每一个新插入的节点，都需要调用 randomLevel 生成一个合理的层数。
    // 该 randomLevel 方法会随机生成 1~MAX_LEVEL 之间的数，且 ：
    //        50%的概率返回 1
    //        25%的概率返回 2
    //      12.5%的概率返回 3 ...
    private int randomLevel() {
        int level = 1;

        while (Math.random() < SKIPLIST_P && level < MAX_LEVEL)
            level += 1;
        return level;
    }

    private static class Node {
        private Object item;
        private Node[] nexts = new Node[MAX_LEVEL];
        private int maxLevel = 0;

        Node(Object e) {
            this.item = e;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{ data: ");
            builder.append(item);
            builder.append("; levels: ");
            builder.append(maxLevel);
            builder.append(" }");

            return builder.toString();
        }
    }

    @SuppressWarnings("unchecked")
    private E item(Node x) {
        return (E) x.item;
    }


}
