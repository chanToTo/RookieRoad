package com.orange.designPattern.strategyPattern;

/**
 * @author orangeC
 * @date 2019/10/18 16:36
 * @desc
 */
public class OpenRefrigerator implements IStrategy{
    @Override
    public void operate() {
        System.out.println("打开冰箱");
    }
}
