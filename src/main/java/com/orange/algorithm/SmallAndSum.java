package com.orange.algorithm;

/**
 * @author orangeC
 * @description 小和问题和逆序对问题
 *小和问题：在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组的小和，求一个数组的小和
 * eg.[1,3,4,2,5]
 * 1左边比1小的数，无
 * 3左边比3小的数，1
 * 4左边比4小的数，1，3
 * 2左边比2小的数，1
 * 5左边比5小的数，1，3，4，2
 * 小和：1+1+3+1+1+3+4+2=16
 *
 * 逆序对问题：一个数组中，左边的数如果比右边的数大，则折两个数构成一个逆序对，打印所有逆序
 * 逆序对问题找出一个数右边哪些数比当前数小
 * @date 2020/4/21 16:24
 */
public class SmallAndSum {

    public static void main(String[] args) {
        int[] arr = {1,3,4,2,5};
        System.out.println(smallSum(arr));
    }

    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return mergeSort(arr, 0, arr.length - 1);
    }

    public static int mergeSort(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        return mergeSort(arr, L, mid) +
        mergeSort(arr, mid + 1, R) +
        merge(arr, L, mid, R);
    }

    public static int merge(int[] arr, int L, int mid, int R) {
        int[] help = new int[R - L + 1];
        int i=0;
        int p1 = L;
        int p2 = mid + 1;
        int res = 0;
        while (p1 <= mid && p2 <= R) {
            res += arr[p1] < arr[p2] ? (R - p2 + 1) * arr[p1] : 0;
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (int j = 0; j < help.length; j++) {
            arr[L + j] = help[j];
        }
        return res;
    }
}
