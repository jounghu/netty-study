package com.dobest.client.handler;

import com.dobest.protocol.packet.ReciveMessagePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: hujiansong
 * @since: 2018/10/23 14:33
 */
@Slf4j
public class ReciveMessageHandler extends SimpleChannelInboundHandler<ReciveMessagePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReciveMessagePacket msg) throws Exception {
        log.info("收到消息,userId={}, userName={},content={}", msg.getFromUserId(), msg.getFromUsername(), msg.getReciveMsg());
    }
}
