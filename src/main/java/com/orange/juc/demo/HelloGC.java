package com.orange.juc.demo;

/**
 * @author orangeC
 * @description
 * @date 2020/1/21 20:02
 */
public class HelloGC {

    public static void main(String[] args) {

        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        System.out.println(totalMemory);
        System.out.println(maxMemory);

    }
}
