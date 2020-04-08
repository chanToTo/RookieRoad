package com.orange.algorithm;

/**
 * @author orangeC
 * @description
 * @date 2020/3/28 21:31
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {17,23,12,13,78,90};
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length - 1; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
