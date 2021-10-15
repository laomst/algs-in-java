package site.laomst.learn.dsag.algo.sort;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.util.ArrayUtil;

import java.util.Arrays;

public class SelectionSort {
    public static void sort(int[] a) {
        System.out.println("SelectionSort.sort -> ");
        if (a == null || a.length <= 1) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[minIndex] > a[j]) {
                    minIndex = j;
                }
            }
            ArrayUtil.swap(a, minIndex, i);
        }
    }

    @Test
    public void sortTest() {
        ArrayUtil.sortTest(SelectionSort::sort);
    }
}
