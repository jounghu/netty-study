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
 * @date: 2018/10/21 20:33
 * @version: V1.0
 */
@Slf4j
public class LoginResponseHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet decode = PacketCodec.decode(byteBuf);
        if (decode instanceof LoginPacket) {
            // 执行登录逻辑检测
            LoginPacket loginPacket = (LoginPacket) decode;
            if (valid(loginPacket)) {
                // 验证通过
                LoginResponsePacket responsePacket = buildResponsePacket("登录成功");
                ByteBuf encode = PacketCodec.encode(ctx.alloc().ioBuffer(), responsePacket);
                ctx.channel().writeAndFlush(encode);
            } else {
                // 提示用户名密码错误
                LoginResponsePacket responsePacket = buildResponsePacket("登录失败");
                ByteBuf encode = PacketCodec.encode(ctx.alloc().ioBuffer(), responsePacket);
                ctx.channel().writeAndFlush(encode);
            }
        } else {
            //
            log.error("协议错误");
        }
    }

    private LoginResponsePacket buildResponsePacket(String msg) {
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setMessage(msg);
        return responsePacket;
    }

    private boolean valid(LoginPacket loginPacket) {
        if ("hjs".equals(loginPacket.getUsername()) && "123456".equals(loginPacket.getPassword())) {
            return true;
        }
        return false;
    }
}
