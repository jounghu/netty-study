package com.dobest.client;

import com.dobest.client.handler.LoginResponseHandler;
import com.dobest.client.handler.ReciveMessageHandler;
import com.dobest.protocol.Spiliter;
import com.dobest.protocol.codec.PacketDecoder;
import com.dobest.protocol.codec.PacketEncoder;
import com.dobest.protocol.packet.LoginPacket;
import com.dobest.protocol.packet.SendMessagePacket;
import com.dobest.util.ConnectUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

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
                        ch.pipeline().addLast(new Spiliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new ReciveMessageHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        connectServer(bootstrap);


    }

    private static void connectServer(Bootstrap bootstrap) {
        bootstrap.connect("localhost", 8000).addListener(future -> {
            if (future.isSuccess()) {
                log.info("Client connect server success , server url:port {}:{}", "localhost", "8000");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
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

    private static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);
        new Thread() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    if (!ConnectUtil.hasLogin(channel)) {
                        System.out.println("请输入用户名:");
                        String username = scanner.nextLine();
                        System.out.println("请输入密码:");
                        String password = scanner.nextLine();

                        LoginPacket loginPacket = new LoginPacket();
                        loginPacket.setUsername(username);
                        loginPacket.setPassword(password);
                        log.info(">>>>>>>>>客户端发送登录请求>>>>>>>>>");
                        channel.writeAndFlush(loginPacket);
                        waitForLoginResponse();
                    } else {
                        String id = scanner.next();
                        String msg = scanner.next();
                        SendMessagePacket sendMessagePacket = new SendMessagePacket();
                        sendMessagePacket.setFromId(id);
                        sendMessagePacket.setRespMsg(msg);
                        channel.writeAndFlush(sendMessagePacket);
                        waitForLoginResponse();
                    }
                }

            }
        }.start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
