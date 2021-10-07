package site.laomst.dsag.algo.sort;

import org.junit.jupiter.api.Test;
import site.laomst.dsag.Util.ArrayUtil;

import java.util.Arrays;

public class InsertionSort {
    public static void sort(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            int value = a[i];
            int j = i - 1;
            while (j >= 0) {
                if (a[j] > value) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
                j--;
            }
            a[j + 1] = value;
        }
    }

    @Test
    public void sortTest() {
        int[] a = ArrayUtil.genIntArray();
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    //            if (i - j >= 0) {
    //                System.arraycopy(a, j, a, j + 1, i - j);
    //            }
//    从前到后搜寻插入位置
    public static void sort2(int[] a) {
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
        int[] a = ArrayUtil.genIntArray();
        System.out.println(Arrays.toString(a));
        sort2(a);
        System.out.println(Arrays.toString(a));
    }

    /**
     * 从前到后查找插入位置实现2
     * sort3的写法不如sort2好
     * @param a
     */
    public static void sort3(int[] a) {
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
        int[] a = ArrayUtil.genIntArray();
        System.out.println(Arrays.toString(a));
        sort3(a);
        System.out.println(Arrays.toString(a));
    }

}
