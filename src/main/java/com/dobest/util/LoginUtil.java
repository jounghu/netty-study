package com.dobest.util;


import io.netty.channel.Channel;

/**
 * @author: hujiansong
 * @since: 2018/10/23 10:13
 */
public class LoginUtil {

    public static void markLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean isLogin(Channel channel) {
        return channel.attr(Attributes.LOGIN).get() != null;
    }

}
