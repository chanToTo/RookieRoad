package com.orange.algorithm;

/**
 * @author orangeC
 * @description
 * @date 2020/3/28 22:00
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = {17,23,12,13,78,90};
        int temp;
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
