package com.orange.juc.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author orangeC
 * @description
 * @date 2020/1/5 20:13
 */

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("******* come in callable");
        return 1024;
    }
}

public class CallableDemo {

    // 多个线程争抢futureTask，一个线程只进去一次
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread());

        Thread t1 = new Thread(futureTask, "AA");
        Thread t2 = new Thread(futureTask1, "BB");
        t1.start();
        int result01 = 100;
        /*while (futureTask.isDone()) {

        }*/
        int result = futureTask.get();
        System.out.println("**** result " + (result01 + result));


    }
}
