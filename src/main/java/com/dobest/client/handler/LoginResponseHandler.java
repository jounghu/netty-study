package com.dobest.client.handler;


import com.dobest.protocol.packet.LoginResponsePacket;
import com.dobest.util.ConnectUtil;
import com.dobest.util.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: hujiansong
 * @since: 2018/10/23 12:08
 */
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isHasSuccess()) {
            log.info("[{}] 登录成功, userId : {}", msg.getUsername(), msg.getUserId());
            ConnectUtil.bindSession(new Session(msg.getUserId(), msg.getUsername()), ctx.channel());
        } else {
            log.info("[{}] 登录失败", msg.getUsername());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端关闭");
    }
}
