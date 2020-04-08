package com.orange.juc;

/**
 * @author orangeC
 * @description
 * @date 2020/2/23 16:18
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {12,45,67,68,69,101,120};
//        System.out.println(get(arr, 0, arr.length));
        System.out.println(binarySea(arr, 130));
    }

    public static int binarySea(int[] arr, int num){
        int low = 0;
        int height = arr.length - 1;

        while (low <= height) {
            int middle = low + (height - low) /2;

            if (num == arr[middle]) {
                return middle;
            } else if (num > arr[middle]) {
                low = middle + 1;
            } else if (num < arr[middle]) {
                height = middle - 1;
            }
        }
        return -1;
    }

    /*public static int get(int[] arr, int start, int end) {
        int x = 70;
        if (x < arr[0]) {
            return -1;
        }
        int middle = (start + end) / 2;
        if (middle == start) {
            return -1;
        }
        if (arr[middle] == x) {
            return middle;
        } else if (x > arr[middle]) {
            return get(arr, middle, end);
        } else {
            return get(arr, start, middle);
        }
    }*/
}
