package com.orange.designPattern.factoryPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:45
 * @desc 工厂模式
 */
public class NvWaNiangNiang {
    public static void main(String[] args) {
        /*Human yellowHuman = HumanFactory.createHuman(YellowHuman.class);
        yellowHuman.cry();
        yellowHuman.laugh();
        yellowHuman.talk();

        Human whiteHuman = HumanFactory.createHuman(WhiteHuman.class);
        whiteHuman.cry();
        whiteHuman.laugh();
        whiteHuman.talk();

        Human blackHuman = HumanFactory.createHuman(BlackHuman.class);
        blackHuman.cry();
        blackHuman.laugh();
        blackHuman.talk();*/
        Human human = HumanFactory.createHuman();
        human.cry();
        human.laugh();
        human.talk();
    }
}
