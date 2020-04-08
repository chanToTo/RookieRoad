package com.orange.designPattern.proxyPattern;

/**
 * @author orangeC
 * @date 2019/10/18 17:01
 * @desc 明星 eg.胡歌
 */
public class Star implements Iproxy {
    @Override
    public void takeAdvertising() {
        System.out.println("明星拍广告");
    }
}
