package com.orange.algorithm;

/**
 * @author orangeC
 * @description 获取最大值
 * @date 2020/4/1 22:14
 */
public class GetMax {

    public static void main(String[] args) {

        int[] arr = {6,17,3,5,7,18,12,14};
        System.out.println(getMax(arr, 0, arr.length - 1));
    }

    private static int getMax(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        int middle = (L + R) / 2;
        int middleLeft = getMax(arr, L, middle);
        int middleRight = getMax(arr, middle + 1, R);
        return Math.max(middleLeft, middleRight);
    }
}
