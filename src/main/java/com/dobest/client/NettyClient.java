package com.dobest.client;

import com.dobest.handler.SendMessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: hujiansong
 * @since: 2018/10/17 11:12
 */
@Slf4j
public class NettyClient {
    private static int retryTimes = 0;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new SendMessageHandler());
                    }
                });

        connectServer(bootstrap);

    }

    private static void connectServer(Bootstrap bootstrap) {
        bootstrap.connect("localhost", 8000).addListener(future -> {
            if (future.isSuccess()) {
                log.info("Client connect server success , server url:port {}:{}", "localhost", "8000");
            } else {
                log.warn("Client connect server failed, attempt retry times {}", retryTimes);
                if (retryTimes++ < 3) {
                    connectServer(bootstrap);
                } else {
                    log.error("Client connect error");
                }
            }
        });
    }
}
