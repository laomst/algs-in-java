package site.laomst.learn.dsag.algo.sort;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.util.ArrayUtil;

public class LmBubbleSort {

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
        ArrayUtil.sortTest(LmBubbleSort::sort, new int[]{1, 1, 1, 1, 1});
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
            boolean continueFlag = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    ArrayUtil.swap(a, j, j + 1);
                    // 本次冒泡有数据交换
                    continueFlag = true;
                }
            }
            // 如果本次冒泡没有发生数据交换，那么排序就可以结束了
            if (!continueFlag) {
                break;
            }
        }
    }

    @Test
    public void sort2Test() {
        ArrayUtil.sortTest(LmBubbleSort::sort2);
    }

    /**
     * 优化2 比较边界
     * 仔细思考一下，如果在一次冒泡中，最后一次交换操作发生在j上，说明j之后的元素都大于j之前的元素
     *
     * 这种写法在有序度比较高的情况下性能会有较为明显的提升，但是如果有序度很低的话，反而会因为记录最后一次交换位置的操作而损失性能
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
        for (int i = 0; i < a.length - 1; i++) {
            // 提前退出的标志位
            boolean continueFlag = false;
            for (int j = 0; j < sortBorder; j++) {
                if (a[j] > a[j + 1]) {
                    ArrayUtil.swap(a, j, j + 1);
                    // 此次冒泡发生了数据交换
                    continueFlag = true;
                    // 上次交换的位置
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            if (!continueFlag) {
                // 本次冒泡没有发生数据交换，那么排序就可以结束了
                break;
            }
        }
    }

    @Test
    public void sort3Test() {
//        ArrayUtil.sortTest(LmBubbleSort::sort3, new int[]{1, 2, 3, 5, 1});
        ArrayUtil.sortTest(LmBubbleSort::sort3);
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
                    ArrayUtil.swap(a, i, j);
                }
            }
        }
    }

    @Test
    public void sortBubbleDownTest() {
        ArrayUtil.sortTest(LmBubbleSort::sortBubbleDown);
    }
}
