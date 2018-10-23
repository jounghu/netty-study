package com.dobest.handler;

import com.dobest.protocol.codec.PacketCodec;
import com.dobest.protocol.packet.LoginPacket;
import com.dobest.protocol.packet.LoginResponsePacket;
import com.dobest.util.ConsoleThread;
import io.netty.buffer.ByteBuf;
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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginPacket loginPacket = new LoginPacket();
        loginPacket.setUsername("hjs");
        loginPacket.setPassword("123456");
        ByteBuf encode = PacketCodec.encode(ctx.alloc().ioBuffer(), loginPacket);
        log.info(">>>>>>>>>客户端发送登录请求>>>>>>>>>");
        ctx.channel().writeAndFlush(encode);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isHasSuccess()) {
            // startThread
            Thread.sleep(10);
            log.info("登陆成功，开启控制台线程>>>>");
            new ConsoleThread(ctx.channel()).start();
        }
    }
}
