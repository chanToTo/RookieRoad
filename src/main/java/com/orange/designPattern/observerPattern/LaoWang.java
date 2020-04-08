package com.orange.designPattern.observerPattern;

public class LaoWang implements Person{

    private String name = "老王";

    @Override
    public void getMessage(String message) {
        System.out.println(name + "接收到小美的电话,内容是： " + message);
    }
}
