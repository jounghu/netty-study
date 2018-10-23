package com.dobest.util;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: hujiansong
 * @since: 2018/10/23 14:25
 */
@Slf4j
public class ConnectUtil {

    private static Map<String, Channel> connects = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        connects.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }


    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            log.info("销毁Session={}", session);
            connects.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return connects.get(userId);
    }

}
