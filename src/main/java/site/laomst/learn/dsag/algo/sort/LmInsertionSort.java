package site.laomst.learn.dsag.algo.sort;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.util.ArrayUtil;
import site.laomst.learn.dsag.util.Best;

public class LmInsertionSort {

    @Best
    public static void sort(int[] a) {
        System.out.println("InsertionSort.sort -> ");
        if (a == null || a.length <= 1) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            // 记录需要插入的值value
            int value = a[i];
            // j 用来记录 value 需要插入的位置, 从有序区的尾部向前查找插入位置
            int j = i - 1;
            for (; j >= 0; j--) {
                // 如果没有找到插入位置，就继续向前移动，同时把元素后移
                if (a[j] > value) {
                    a[j + 1] = a[j];
                } else {
                    // 否则，value比a[j]大了，它应该插入到a[j]后面，所以j + 1 就是value需要插入的位置
                    break;
                }
            }
            a[j + 1] = value;
        }
    }

    @Test
    public void sortTest() {
        ArrayUtil.sortTest(LmInsertionSort::sort);
    }

    /**
     * 插入排序实现2，从有序区的前面查找插入位置
     * @param a
     */
    public static void sort2(int[] a) {
        System.out.println("InsertionSort.sort2 -> ");
        if (a == null || a.length <= 1) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            // 记录要插入的值
            int value = a[i];
            int j = 0;
            // 从前到后找到需要插入的位置
            for (; j < i; j++) {
                // value比j小，所以value应该插入到a[j]的位置，j就是value要插入的位置
                if (value < a[j]) {
                    break;
                }
            }
            // 从插入位置开始，所有的元素后移一位
            for (int k = i; k > j; k--) {
                a[k] = a[k - 1];
            }
            // 插入值
            a[j] = value;
        }
    }


    @Test
    public void sort2Test() {
        ArrayUtil.sortTest(LmInsertionSort::sort2);
    }

    /**
     * 从有序去的前面查找插入位置，采用使用交换代替移动元素的方式进行操作
     * sort3的写法不如sort2好
     * @param a
     */
    public static void sort3(int[] a) {
        System.out.println("InsertionSort.sort3 -> ");
        if (a == null || a.length <= 1) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            int value = a[i];
            int j = 0;
            for (; j < i; j++) {
                // 如果value比a[j] 大，则不满足插入条件，继续往下走
                if (value >= a[j]) {
                    continue;
                }
                // 因为这是在有序区插入，后面会发生n多次的交换，所以这个写法的效率不如sort2
                int tmp = a[j];
                a[j] = value;
                value = tmp;
            }
            a[i] = value;
        }
    }

    @Test
    public void sort3Test() {
        ArrayUtil.sortTest(LmInsertionSort::sort3);
    }

}
