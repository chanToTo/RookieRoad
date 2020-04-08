package com.orange.designPattern.strategyPattern;

/**
 * @author orangeC
 * @date 2019/10/18 16:41
 * @desc
 */
public class PutElephant implements IStrategy{
    @Override
    public void operate() {
        System.out.println("把大象放进冰箱");
    }
}
