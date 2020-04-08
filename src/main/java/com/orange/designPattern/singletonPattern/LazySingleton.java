package com.orange.designPattern.singletonPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:15
 * @desc 懒汉式单例 线程不安全，延迟初始化，严格意义上不是单例模式
 * 假使有线程A和线程B，在A判断instance是否为空时，走进if分支，
 * B线程在毫秒之差也到了判断instance是否为空，并且走进if分支，
 * LazySingleton对象就会被创建两次，不是严格的单例模式
 */
public class LazySingleton {
    private static LazySingleton instance = null;

    private LazySingleton() {
    }

    private static LazySingleton getInstance() {
        if (instance == null) {
            return new LazySingleton();
        }
        return instance;
    }
}
