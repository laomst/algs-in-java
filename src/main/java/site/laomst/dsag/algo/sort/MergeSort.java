package site.laomst.dsag.algo.sort;

import org.junit.jupiter.api.Test;
import site.laomst.dsag.Util.ArrayUtil;

import java.util.Arrays;

public class MergeSort {
    public static void sort(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        sort(a, 0, a.length-1);
    }

    private static void sort(int[] a, int start, int end) {
        if (start >= end) {
            return;
        }

        int q = (start + end) / 2;
        sort(a, start, q);
        sort(a, q + 1, end);

        // 两部分按位比较，按序放入临时数组中
        int i = start, j = q + 1, k = 0;
        int[] tmp = new int[end - start + 1];
        while (i <= q && j <= end) {
            if (a[i] < a[j]) {
                tmp[k++] = a[i++];
            } else {
                tmp[k++] = a[j++];
            }
        }
        // 假设 左边的数组有剩余的元素
        int start1 = i, end1 = q;
        // 如果不是左边的数组有剩余的元素，那么就重新赋值
        if (j <= end) {
            start1 = j;
            end1 = end;
        }
        // 把剩余的元素都放入临时数组中，注意，每一个部分都已经是有序的
        while (start1 <= end1) {
            tmp[k++] = a[start1++];
        }

        // 把 临时数组中的内容复制到原数组中。
        for (int i1 = 0; i1 <= end - start; i1++) {
            a[start + i1] = tmp[i1];
        }
//        if (end - start + 1 >= 0) {
//            System.arraycopy(tmp, 0, a, start, end - start + 1);
//        }
    }

    @Test
    public void sortTest() {
        int[] a = ArrayUtil.genIntArray();
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    /**
     * 优化，不是每次都申请一个新的临时数组，而是开头直接申请一个和原数组一样大小的临时数组，供下面使用
     * @param a
     */
    public static void sort2(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        int[] tmp = new int[a.length];
        sort2(a, 0, a.length-1, tmp);
    }

    private static void sort2(int[] a, int start, int end, int[] tmp) {
        if (start >= end) {
            return;
        }

        int q = (start + end) / 2;
        sort2(a, start, q, tmp);
        sort2(a, q + 1, end, tmp);

        // 两部分按位比较，按序放入临时数组中
        int i = start, j = q + 1, k = 0;
        while (i <= q && j <= end) {
            if (a[i] < a[j]) {
                tmp[k++] = a[i++];
            } else {
                tmp[k++] = a[j++];
            }
        }
        // 假设 左边的数组有剩余的元素
        int start1 = i, end1 = q;
        // 如果不是左边的数组有剩余的元素，那么就重新赋值
        if (j <= end) {
            start1 = j;
            end1 = end;
        }
        // 把剩余的元素都放入临时数组中，注意，每一个部分都已经是有序的
        while (start1 <= end1) {
            tmp[k++] = a[start1++];
        }

        // 把 临时数组中的内容复制到原数组中。
//        for (int i1 = 0; i1 <= end - start; i1++) {
//            a[start + i1] = tmp[i1];
//        }
        if (end - start + 1 >= 0) {
            System.arraycopy(tmp, 0, a, start, end - start + 1);
        }
    }

    @Test
    public void sort2Test() {
        int[] a = ArrayUtil.genIntArray();
        System.out.println(Arrays.toString(a));
        sort2(a);
        System.out.println(Arrays.toString(a));
    }
}
