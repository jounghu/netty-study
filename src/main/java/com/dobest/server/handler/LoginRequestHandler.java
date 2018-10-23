package com.dobest.server.handler;

import com.dobest.protocol.packet.LoginPacket;
import com.dobest.protocol.packet.LoginResponsePacket;
import com.dobest.util.ConnectUtil;
import com.dobest.util.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @Package: com.dobest.handler
 * @author: Jounghu
 * @date: 2018/10/21 20:29
 * @version: V1.0
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPacket msg) throws Exception {
        LoginResponsePacket loginResp = new LoginResponsePacket();
        loginResp.setUsername(msg.getUsername());
        if (valid(msg)) {
            // 绑定UerId -> Channel
            String userId = UUID.randomUUID().toString().split("-")[0];
            loginResp.setUserId(userId);
            loginResp.setHasSuccess(true);
            ConnectUtil.bindSession(new Session(userId, msg.getUsername()), ctx.channel());
        } else {
            loginResp.setMessage("用户名或者密码错误");
            loginResp.setHasSuccess(false);
        }

        log.info("[登陆成功] 用户名={},密码={}>>>>>>", msg.getUsername(), msg.getPassword());
        ctx.channel().writeAndFlush(loginResp);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ConnectUtil.unBindSession(ctx.channel());
    }


    private boolean valid(LoginPacket msg) {
        return true;
    }


}
