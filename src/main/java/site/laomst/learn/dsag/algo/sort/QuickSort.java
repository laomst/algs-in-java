package site.laomst.learn.dsag.algo.sort;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.util.ArrayUtil;
import site.laomst.learn.dsag.util.Best;

import java.util.Arrays;

public class QuickSort {
    @Best
    public static void sort(int[] a) {
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

        for (int j = start; j <= end - 1; j++) {
            if (a[j] < pivot) {
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
        int[] a = ArrayUtil.genIntArray();
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
