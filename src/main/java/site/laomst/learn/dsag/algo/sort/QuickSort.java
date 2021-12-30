package site.laomst.learn.dsag.algo.sort;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.util.ArrayUtil;
import site.laomst.learn.dsag.util.Best;

public class QuickSort {
    @Best
    public static void sort(int[] a) {
        System.out.println("QuickSort.sort -> ");
        if (a == null || a.length <= 1) {
            return;
        }
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int start, int end) {
        if (start >= end) {
            return;
        }

        // 排序区间中的最后一个值作为中心轴
        int pivot = a[end];
        // 用一个i，记录pivot的位置
        int i = start;

        // 这个地方刚开始可能会有一些i和j相同的情况出现，但是只要不是本来就有序的，运行几次之后就会出现i比j小的情况
        for (int j = start; j <= end - 1; j++) {
            if (a[j] < pivot) {
                // 把比中心轴小的数据交换到i的位置上，i自增1，这个时候i代表pivot应该处于的位置，其左侧也都是比pivot小的值
                ArrayUtil.swap(a, i, j);
                ++i;
            }
        }

        // 把 pivot 放到应该的位置上
        ArrayUtil.swap(a, end, i);

        sort(a, start, i - 1);
        sort(a, i + 1, end);
    }

    @Test
    public void testSort() {
        ArrayUtil.sortTest(QuickSort::sort);
    }
}
