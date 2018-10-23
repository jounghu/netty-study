package com.dobest.server.handler;

import com.dobest.protocol.packet.ReciveMessagePacket;
import com.dobest.protocol.packet.SendMessagePacket;
import com.dobest.util.ConnectUtil;
import com.dobest.util.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Package: com.dobest.handler
 * @author: Jounghu
 * @date: 2018/10/21 20:29
 * @version: V1.0
 */
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<SendMessagePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessagePacket msg) throws Exception {
        String content = msg.getRespMsg();

        String id = msg.getFromId();
        // 取出消息来源
        Session session = ConnectUtil.getSession(ctx.channel());

        // 拼装消息
        ReciveMessagePacket reciveMessagePacket = buildRecivePacket(session, content);

        // 获取消息去向Channel
        Channel channel = ConnectUtil.getChannel(id);
        if (channel != null && ConnectUtil.hasLogin(channel)) {
            channel.writeAndFlush(reciveMessagePacket);
        } else {
            log.info("用户不在线，消息发送失败!");
        }

    }

    private ReciveMessagePacket buildRecivePacket(Session session, String reMsg) {
        ReciveMessagePacket reciveMsg = new ReciveMessagePacket();
        reciveMsg.setReciveMsg(reMsg);
        reciveMsg.setFromUserId(session.getUserId());
        reciveMsg.setFromUsername(session.getUsername());
        return reciveMsg;
    }
}
