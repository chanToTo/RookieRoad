package com.orange.algorithm.offer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author orangeC
 * @description 剑指offer 03.找出数组中重复的数字
 * eg.
 * 输入：
 * [2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 * @date 2020/5/12 20:25
 */
public class FindRepeatNumber {

    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 0, 2, 5, 3};
        System.out.println(findRepeatNumber(arr));
    }
    /**
     * 执行时间 3 ms 内存消耗 47.6 MB
     * @param nums
     * @return
     */
    public static int findRepeatNumber(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == nums[i + 1]) {
                return nums[i];
            }
        }
        return -1;
    }

    /**
     * 执行时间 11 ms	内存消耗 48.9 MB
     * @param nums
     * @return
     */
   /* public static int findRepeatNumber(int[] nums) {
        Set set = new HashSet();
        for (int i = 0; i < nums.length; i++) {
            if (!set.contains(nums[i])) {
                set.add(nums[i]);
            } else {
                return nums[i];
            }
        }
        return -1;
    }*/
}
