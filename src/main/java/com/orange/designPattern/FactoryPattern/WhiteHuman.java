package com.orange.designPattern.FactoryPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:41
 * @desc
 */
public class WhiteHuman implements Human{
    @Override
    public void laugh() {
        System.out.println("白色人种会笑");
    }

    @Override
    public void cry() {
        System.out.println("白色人种会哭");
    }

    @Override
    public void talk() {
        System.out.println("白色人种会说话");
    }
}
