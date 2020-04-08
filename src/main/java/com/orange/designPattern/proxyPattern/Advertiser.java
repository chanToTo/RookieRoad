package com.orange.designPattern.proxyPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:03
 * @desc 广告商
 */
public class Advertiser {
    public static void main(String[] args) {
        Broker broker = new Broker();
        broker.takeAdvertising();
    }
}
