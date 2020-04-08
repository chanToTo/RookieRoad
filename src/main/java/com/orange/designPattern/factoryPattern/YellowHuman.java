package com.orange.designPattern.factoryPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:40
 * @desc
 */
public class YellowHuman implements Human {
    @Override
    public void laugh() {
        System.out.println("黄色人种会笑");
    }

    @Override
    public void cry() {
        System.out.println("黄色人种会哭");
    }

    @Override
    public void talk() {
        System.out.println("黄色人种会说话");
    }
}
