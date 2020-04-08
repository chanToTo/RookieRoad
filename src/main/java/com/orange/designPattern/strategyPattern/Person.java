package com.orange.designPattern.strategyPattern;

/**
 * @author orangeC
 * @date 2019/10/18 16:42
 * @desc
 */
public class Person {
    public static void main(String[] args) {
        // 打开冰箱
        Context context = new Context(new OpenRefrigerator());
        context.operate();
        // 把大象放进冰箱
        context = new Context(new PutElephant());
        context.operate();
        // 关上冰箱
        context = new Context(new CloseRefrigerator());
        context.operate();
    }
}
