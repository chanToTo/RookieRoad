package com.orange.juc.demo;

import com.orange.juc.enums.CountryEnum;

import java.util.concurrent.CountDownLatch;

/**
 * @author orangeC
 * @description
 * @date 2019/12/29 18:59
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6 ; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 国，被灭");
                countDownLatch.countDown();
                }, CountryEnum.forearch(i).getRetMessage()).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t秦帝国，一统华夏");
    }
}
