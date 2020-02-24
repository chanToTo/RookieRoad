package com.orange.jvm.demo;

import java.util.concurrent.*;

/**
 * @author orangeC
 * @description
 * @date 2020/1/19 8:53
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(2, 5, 1L,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(3), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        try {
            for (int i = 1; i <= 5; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理业务");
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public static void threadPoolInit() {
        //        ExecutorService threadPool = Executors.newFixedThreadPool(5);// 一池添加固定5个线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();// 一池添加1个线程
        ExecutorService threadPool = Executors.newCachedThreadPool(); // 一池添加多个线程

        // 模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程

        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\n 办理业务");
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
