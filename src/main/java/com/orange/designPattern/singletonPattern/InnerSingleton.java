package com.orange.designPattern.singletonPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:27
 * @desc 静态内部单例模式
 */
public class InnerSingleton {
    private InnerSingleton() {
    }
    public static InnerSingleton getInstance(){
        return InnerSingleton.getInstance();
    }
    private static class Inner{
        private static final InnerSingleton instance = new InnerSingleton();
    }
}
