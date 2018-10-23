package com.dobest.handler;

import com.dobest.protocol.codec.PacketCodec;
import com.dobest.protocol.packet.LoginPacket;
import com.dobest.protocol.packet.LoginResponsePacket;
import com.dobest.util.LoginUtil;
import io.netty.buffer.ByteBuf;
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
public class LoginHandlerEnhance extends SimpleChannelInboundHandler<LoginPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPacket msg) throws Exception {

        log.info(">>>>>>>>>>>>>>>>收到客户端登录请求>>>>>>>>>>>>>>>>");
        LoginResponsePacket loginResp = login(ctx.channel(), msg);
        ByteBuf encode = PacketCodec.encode(ctx.alloc().ioBuffer(), loginResp);
        ctx.channel().writeAndFlush(encode);
    }

    private LoginResponsePacket login(Channel channel, LoginPacket msg) {
        LoginResponsePacket loginResp = new LoginResponsePacket();
        if (msg.getUsername().equals("hjs") && msg.getPassword().equals("123456")) {
            log.info(">>>>>>>>>>>>>>>>客户端登录成功>>>>>>>>>>>>>>>>");
            loginResp.setMessage("登陆成功");
            loginResp.setHasSuccess(true);
            LoginUtil.markLogin(channel);
        } else {
            loginResp.setMessage("用户名或者密码错误");
            loginResp.setHasSuccess(false);
        }
        return loginResp;
    }


}
