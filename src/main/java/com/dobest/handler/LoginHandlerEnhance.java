package com.dobest.handler;

import com.dobest.protocol.codec.PacketCodec;
import com.dobest.protocol.packet.LoginPacket;
import com.dobest.protocol.packet.LoginResponsePacket;
import com.dobest.util.ConsoleThread;
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
        LoginResponsePacket loginResp = login(ctx.channel(), msg);
        ByteBuf encode = PacketCodec.encode(ctx.alloc().ioBuffer(), loginResp);
        ctx.channel().writeAndFlush(encode);
    }

    private LoginResponsePacket login(Channel channel, LoginPacket msg) {
        LoginResponsePacket loginResp = new LoginResponsePacket();
        if (msg.getUsername().equals("hjs") && msg.getPassword().equals("123456")) {
            loginResp.setMessage("登陆成功");
            LoginUtil.markLogin(channel);
            startConsoleThread(new ConsoleThread(channel));
        } else {
            loginResp.setMessage("用户名或者密码错误");
        }
        return loginResp;
    }

    private void startConsoleThread(ConsoleThread consoleThread) {
        consoleThread.start();
    }
}
