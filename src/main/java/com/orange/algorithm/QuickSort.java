package com.orange.algorithm;

/**
 * @author orangeC
 * @description 快速排序 o(n logN)
 * @date 2020/5/12 17:51
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {13,14,12,1,2,15,5,8};
        quick(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
    private static void quick(int[] arr, int left, int right) {
        if (arr == null && arr.length == 0) {
            return;
        }
        if (left > right) {
            return;
        }
        int key = arr[left];
        int l = left;
        int r = right;
        while (l != r) {
            while (arr[r] >= key && l < r) {
                r--;
            }
            while (arr[l] <= key && l < r) {
                l++;
            }
            int temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
        }
        arr[left] = arr[l];
        arr[l] = key;
        quick(arr, left, l - 1);
        quick(arr, r + 1, right);
    }
}
