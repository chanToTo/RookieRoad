package com.orange.designPattern.strategyPattern;

/**
 * @author orangeC
 * @date 2019/10/18 16:41
 * @desc
 */
public class CloseRefrigerator implements IStrategy{
    @Override
    public void operate() {
        System.out.println("关上冰箱");
    }
}
