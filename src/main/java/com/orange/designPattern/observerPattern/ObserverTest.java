package com.orange.designPattern.observerPattern;

/**
 * 观察者模式
 * 老王和老李皆为观察者时刻关注小美的消息，在小美发送消息时，老王老李会立即获取消息做相应的处理
 */
public class ObserverTest {

    public static void main(String[] args) {
        XiaoMei xiaoMei = new XiaoMei();
        xiaoMei.addPerson(new LaoLi());
        xiaoMei.addPerson(new LaoWang());
        xiaoMei.notifyPerson();
    }
}
