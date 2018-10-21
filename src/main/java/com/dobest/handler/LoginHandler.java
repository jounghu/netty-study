package com.dobest.handler;

import com.dobest.protocol.codec.PacketCodec;
import com.dobest.protocol.packet.LoginPacket;
import com.dobest.protocol.packet.LoginResponsePacket;
import com.dobest.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Package: com.dobest.handler
 * @author: Jounghu
 * @date: 2018/10/21 20:29
 * @version: V1.0
 */
@Slf4j
public class LoginHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 执行发送用户名还有密码
        LoginPacket loginPacket = new LoginPacket();
        loginPacket.setUsername("hjs");
        loginPacket.setPassword("123456");
        ByteBuf encode = PacketCodec.encode(ctx.alloc().ioBuffer(), loginPacket);
        ctx.writeAndFlush(encode);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet decode = PacketCodec.decode(byteBuf);
        if (decode instanceof LoginResponsePacket) {
            LoginResponsePacket resp = (LoginResponsePacket) decode;
            String message = resp.getMessage();
            log.info(message);
        } else {
            log.error("协议错误");
        }
    }
}
