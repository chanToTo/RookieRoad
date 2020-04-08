package com.orange.designPattern.strategyPattern;

/**
 * @author orangeC
 * @date 2019/10/18 16:42
 * @desc
 */
public class Context {
    private IStrategy iStrategy;
    public Context(IStrategy iStrategy){
        this.iStrategy = iStrategy;
    }

    public void operate(){
        this.iStrategy.operate();
    }
}
