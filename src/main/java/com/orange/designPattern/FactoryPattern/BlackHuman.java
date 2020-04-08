package com.orange.designPattern.FactoryPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:42
 * @desc
 */
public class BlackHuman implements Human{
    @Override
    public void laugh() {
        System.out.println("黑色人种会笑");
    }

    @Override
    public void cry() {
        System.out.println("黑色人种会哭");
    }

    @Override
    public void talk() {
        System.out.println("黑色人种会说话");
    }
}
