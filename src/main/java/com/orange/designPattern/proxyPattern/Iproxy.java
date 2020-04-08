package com.orange.designPattern.proxyPattern;

/**
 * @author orangeC
 * @date 2019/10/18 16:55
 * @desc 代理模式接口 广告商想要找胡歌拍广告，只能找到胡歌的经纪人，经纪人作为代理，实际做事（拍广告）的是胡歌，体现java的多态
 */
public interface Iproxy {

    /**
     * 接广告
     */
    void takeAdvertising();
}
