package com.orange.jvm.demo;

/**
 * @author orangeC
 * @description
 * @date 2020/1/5 19:34
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AA打印5次，BB打印10次，CC打印15次,10轮
 */
class ShareResource{
    private int number = 1;//a:1 b:2 c:3
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            // 1判断
            while (number != 1) {
                condition1.await();
            }
            // 2干活
            for (int i = 1; i <= 5 ; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 3通知
            number = 2;
            condition2.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            // 1判断
            while (number != 2) {
                condition2.await();
            }
            // 2干活
            for (int i = 1; i <= 10 ; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 3通知
            number = 3;
            condition3.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            // 1判断
            while (number != 3) {
                condition3.await();
            }
            // 2干活
            for (int i = 1; i <= 15 ; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 3通知
            number = 1;
            condition1.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print5();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print10();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.print15();
            }
        }, "CC").start();
    }
}
