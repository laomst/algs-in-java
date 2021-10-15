package site.laomst.learn.dsag.algo.sort;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.util.ArrayUtil;

import java.util.Arrays;

public class BubbleSort {

    /**
     * 最初的实现，没有任何优化
     *
     * @param a
     */
    public static void sort(int[] a) {
        System.out.println("BubbleSort.sort -> ");
        if (a == null || a.length <= 1) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    ArrayUtil.swap(a, j, j + 1);
                }
            }
        }
    }

    @Test
    public void sortTest() {
        ArrayUtil.sortTest(BubbleSort::sort);
    }

    /**
     * 优化1，如果在一次冒泡中，没有发生任何的数据交换，那么就可以提前结束了
     *
     * @param a
     */
    public static void sort2(int[] a) {
        System.out.println("BubbleSort.sort2 -> ");
        if (a == null || a.length <= 1) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            // 是否可以提前退出的标志位
            boolean flag = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    ArrayUtil.swap(a, j, j + 1);
                    // 本次冒泡有数据交换
                    flag = true;
                }
            }
            // 如果本次冒泡没有发生数据交换，那么排序就可以结束了
            if (!flag) {
                break;
            }
        }
    }

    @Test
    public void sort2Test() {
        ArrayUtil.sortTest(BubbleSort::sort2);
    }

    /**
     * 冒泡排序改进，在每一次冒泡之后记录最后一次元素交换的位置，作为下次比较的边界
     * 对于边界外部的元素，在下次迭代的时候无需进行比较
     *
     * @param a
     */
    public static void sort3(int[] a) {
        System.out.println("BubbleSort.sort3 -> ");
        if (a == null || a.length <= 1) {
            return;
        }
        // 最后一次交换的位置
        int lastExchangeIndex = 0;
        // 无序数据的边界，每次只需要比较到这里就可以退出
        int sortBorder = a.length - 1;
        for (int i = 0; i < sortBorder; i++) {
            // 提前退出的标志位
            boolean flag = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    ArrayUtil.swap(a, j, j + 1);
                    // 此次冒泡发生了数据交换
                    flag = true;
                    // 上次交换的位置
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            if (!flag) {
                // 本次冒泡没有发生数据交换，那么排序就可以结束了
                break;
            }
        }
    }

    @Test
    public void sort3Test() {
        ArrayUtil.sortTest(BubbleSort::sort3);
    }

    /**
     * 向下冒泡
     * 从a[0]开始，用这个元素去跟后面的所有元素比较，如果发现这个元素大于后面的某个元素，则交换。
     *
     * @param a
     */
    public static void sortBubbleDown(int[] a) {
        System.out.println("BubbleSort.sortBubbleDown -> ");
        if (a == null || a.length <= 1) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    ArrayUtil.swap(a, j, j + 1);
                }
            }
        }
    }

    @Test
    public void sortBubbleDownTest() {
        ArrayUtil.sortTest(BubbleSort::sortBubbleDown);
    }
}
