package com.dobest.util;

import com.dobest.protocol.codec.PacketCodec;
import com.dobest.protocol.packet.MessagePacket;
import io.netty.buffer.ByteBuf;
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
            System.out.println("请发送消息给客户端(Stop to stop): ");
            String msg = scanner.nextLine();
            if (msg.equals("stop")) {
                break;
            }
            MessagePacket messagePacket = new MessagePacket();
            messagePacket.setRespMsg(msg);
            ByteBuf encode = PacketCodec.encode(this.channel.alloc().ioBuffer(), messagePacket);
            this.channel.writeAndFlush(encode);
        }

    }
}
