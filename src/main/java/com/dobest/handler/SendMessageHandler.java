package com.dobest.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author: hujiansong
 * @since: 2018/10/17 13:12
 */
@Slf4j
public class SendMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端发送消息
        ByteBuf buffer = ctx.channel().alloc().buffer();
        buffer.writeBytes("你好，服务器，我是胡建松".getBytes(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("收到消息: {}", byteBuf.toString(StandardCharsets.UTF_8));
    }
}
