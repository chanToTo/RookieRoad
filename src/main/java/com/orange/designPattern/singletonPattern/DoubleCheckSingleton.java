package com.orange.designPattern.singletonPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:25
 * @desc 双重锁模式 线程安全，延迟初始化。这种方式采用双锁机制，安全且在多线程情况下能保持高性能
 */
public class DoubleCheckSingleton {
    private volatile static DoubleCheckSingleton doubleCheckSingleton;

    private DoubleCheckSingleton() {
    }
    public static DoubleCheckSingleton getInstance(){
        if(doubleCheckSingleton == null){
            synchronized (DoubleCheckSingleton.class){
                if(doubleCheckSingleton == null){
                    doubleCheckSingleton = new DoubleCheckSingleton();
                }
            }
        }
        return doubleCheckSingleton;
    }
}
