package site.laomst.learn.dsag.Util;

public class ArrayUtil {
    public static int[] genIntArray() {
//        return new int[]{49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 1};
        return new int[]{1, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 49};
    }

    public static void swap(int[] a, int index1, int index2) {
        int tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }
}
