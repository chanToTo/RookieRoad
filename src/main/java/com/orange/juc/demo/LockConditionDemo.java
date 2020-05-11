package com.orange.juc.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author orangeC
 * @description
 * @date 2020/5/11 16:17
 */
public class LockConditionDemo {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();
    private  static int num=1;

    private void printA() {
        lock.lock();
        try {
            while (num != 1) {
                conditionA.await();
            }
            System.out.print("A");
            num = 2;
            conditionB.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void printB() {
        lock.lock();
        try {
            while (num != 2) {
                conditionB.await();
            }
            System.out.print("B");
            num = 3;
            conditionC.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    private  void printC() {
        lock.lock();
        try {
            while (num != 3) {
                conditionC.await();
            }
            System.out.print("C");
            num = 1;
            conditionA.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        LockConditionDemo lockConditionDemo = new LockConditionDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lockConditionDemo.printA();
            }, "A").start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lockConditionDemo.printB();
            }, "B").start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lockConditionDemo.printC();
            }, "C").start();
        }

    }
}
