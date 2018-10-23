package com.dobest.util;

import com.dobest.protocol.packet.SendMessagePacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author: hujiansong
 * @since: 2018/10/23 10:30
 */
public class ConsoleThread extends Thread {

    Channel channel;


    public ConsoleThread(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请发送消息(Stop to stop): ");
            String msg = scanner.nextLine();
            SendMessagePacket messagePacket = new SendMessagePacket();
            messagePacket.setRespMsg(msg);
            this.channel.writeAndFlush(messagePacket);
        }

    }
}
