package com.orange.juc;

/**
 * @author orangeC
 * @description
 * @date 2020/2/23 9:44
 */
public class T2 {

    public static void main(String[] args) {
        // 最大 -Xmx
        System.out.println(Runtime.getRuntime().maxMemory());
        // 默认初始大小 -Xms
        System.out.println(Runtime.getRuntime().totalMemory());
        // cpu核数
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
