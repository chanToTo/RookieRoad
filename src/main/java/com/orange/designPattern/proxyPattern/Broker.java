package com.orange.designPattern.proxyPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:00
 * @desc 经纪人
 */
public class Broker implements Iproxy {
    private Iproxy iproxy;

    public Broker() {
        this.iproxy = new Star();
    }

    public Broker(Iproxy iproxy) {
        this.iproxy = iproxy;
    }

    @Override
    public void takeAdvertising() {
        this.iproxy.takeAdvertising();
    }
}
