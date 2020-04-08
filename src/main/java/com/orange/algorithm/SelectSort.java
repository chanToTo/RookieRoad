package com.orange.algorithm;

/**
 * @author orangeC
 * @description
 * @date 2020/3/28 21:44
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {17,23,12,13,78,90};
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            int middleIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                middleIndex = arr[j] < arr[middleIndex] ? j : middleIndex;
            }
            temp = arr[i];
            arr[i] = arr[middleIndex];
            arr[middleIndex] = temp;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
