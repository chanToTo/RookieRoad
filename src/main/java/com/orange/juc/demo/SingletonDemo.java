package com.orange.juc.demo;

/**
 * @author orangeC
 * @description
 * @date 2019/12/4 21:06
 */
public class SingletonDemo {
    private static SingletonDemo instance = null;
    private SingletonDemo(){
        System.out.println(Thread.currentThread() + "创造实例");
    }
    private static SingletonDemo getInstance(){
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();

                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }).start();
        }
    }
}
