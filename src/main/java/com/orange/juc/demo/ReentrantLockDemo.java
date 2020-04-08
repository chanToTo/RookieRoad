package com.orange.juc.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author orangeC
 * @description
 * @date 2019/12/25 22:29
 */

class Phone implements Runnable{
    public synchronized  void sendSms() {
        System.out.println(Thread.currentThread().getId() + "\t invoke sendSms()");
        sendEmail();
    }
    public synchronized  void sendEmail() {
        System.out.println(Thread.currentThread().getId() + "\t invoke sendEmail()");
    }
    ReentrantLock reentrantLock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }

    public void get(){
        reentrantLock.lock();

        try {
            System.out.println(Thread.currentThread().getId() + "\t invoke get()");
            set();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void set(){
        reentrantLock.lock();

        try {
            System.out.println(Thread.currentThread().getId() + "\t invoke set()");
        } finally {
            reentrantLock.unlock();
        }
    }
}

public class ReentrantLockDemo {

    public static void main(String[] args) {
        Phone phone =new Phone();
        new Thread(() -> {
            phone.sendSms();
        },"t1").start();
        new Thread(() -> {
            phone.sendEmail();
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(phone, "t3");
        Thread thread1 = new Thread(phone, "t4");
        thread.start();
        thread1.start();
    }
}
