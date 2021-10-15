package site.laomst.learn.dsag.algo.sort;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.util.ArrayUtil;
import site.laomst.learn.dsag.util.Best;

import java.util.Arrays;

public class InsertionSort {

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
                if (a[j] > value) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = value;
        }
    }

    @Test
    public void sortTest() {
        ArrayUtil.sortTest(InsertionSort::sort);
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
            int value = a[i];
            int j = 0;
            for (; j < i; j++) {
                if (value < a[j]) {
                    break;
                }
            }
            for (int k = i; k > j; k--) {
                a[k] = a[k - 1];
            }
            a[j] = value;
        }
    }


    @Test
    public void sort2Test() {
        ArrayUtil.sortTest(InsertionSort::sort2);
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
                if (value >= a[j]) {
                    continue;
                }
                int tmp = a[j];
                a[j] = value;
                value = tmp;
            }
            a[i] = value;
        }
    }

    @Test
    public void sort3Test() {
        ArrayUtil.sortTest(InsertionSort::sort3);
    }

}
