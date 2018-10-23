package com.dobest.server;

import com.dobest.handler.AuthHandler;
import com.dobest.handler.LoginHandlerEnhance;
import com.dobest.handler.MessageHandlerEnhance;
import com.dobest.protocol.Spiliter;
import com.dobest.protocol.codec.PacketDecoder;
import com.dobest.protocol.codec.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: hujiansong
 * @since: 2018/10/17 10:42
 */
@Slf4j
public class NettyServer {
    public static void main(String[] args) {
        // 负责监听端口, accept新连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 负责处理连接读写
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                // 指定NIO线程模型
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new Spiliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginHandlerEnhance());
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new MessageHandlerEnhance());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        int initPort = 8000;
        bindPort(serverBootstrap, initPort);
    }

    private static void bindPort(ServerBootstrap serverBootstrap, int initPort) {
        try {
            serverBootstrap.bind(initPort);
            log.info("Netty Server start, bind port " + initPort);
        } catch (Exception e) {
            log.warn("连接失败，retry");
            serverBootstrap.bind(++initPort);
        }
    }
}
