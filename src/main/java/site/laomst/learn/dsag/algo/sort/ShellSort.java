package site.laomst.learn.dsag.algo.sort;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.Util.ArrayUtil;

import java.util.Arrays;

/**
 * 希尔排序
 * {17, 3, 1, 9, 23, 78, 33};
 * 第一趟：step = 3, i = 3, j = 0
 */
public class ShellSort {
    public static void sort(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        int step = a.length / 2;
        while (step >= 1) {
            for (int i = step; i < a.length; i++) {
                int value = a[i];
                int j = i - step;
                for (; j >= 0; j -= step) {
                    if (value < a[j]) {
                        a[j + step] = a[j];
                    } else {
                        break;
                    }
                }
                a[j + step] = value;
            }

            step /= 2;
        }
    }

    @Test
    public void sortTest() {
        int[] a = ArrayUtil.genIntArray();
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }
}
