package com.orange.designPattern.singletonPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:19
 * @desc 饿汉模式(通用模式) 线程安全 static初始化对象，保证该类在被使用时只会被创建一个对象
 */
public class HungrySingleton {
    private static final HungrySingleton hungrySingleton = new HungrySingleton();

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return hungrySingleton;
    }
}
