package com.dobest.protocol.codec;

import com.dobest.protocol.Command;
import com.dobest.protocol.packet.LoginPacket;
import com.dobest.protocol.packet.LoginResponsePacket;
import com.dobest.protocol.packet.MessagePacket;
import com.dobest.protocol.packet.Packet;
import com.dobest.protocol.serialize.Serializer;
import io.netty.buffer.ByteBuf;

/**
 * @Package: com.dobest.protocol.codec
 * @author: Jounghu
 * @date: 2018/10/21 20:05
 * @version: V1.0
 */
public class PacketCodec {

    public static final int MAGINC_NUM = 150_6187;

    // 自定义协议  MAGIC_NUM(4) + VERSION(1) + ALG_NAME(1) + COMMAND(1) + DATA_LENGTH(4) + DATA_BODY


    public static ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        Serializer jsonSerializer = Serializer.DEFAULT;
        byte[] data = jsonSerializer.serialize(packet);

        byteBuf.writeInt(MAGINC_NUM);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(jsonSerializer.getSerializer());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
        return byteBuf;
    }

    public static Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);

        byte algName = byteBuf.readByte();
        byte command = byteBuf.readByte();

        int length = byteBuf.readInt();

        byte[] dataBytes = new byte[length];
        byteBuf.readBytes(dataBytes);

        Class<? extends Packet> requestType = getRequestType(command);
        // algName 获取对应的Serializer

        Packet o = (Packet) Serializer.DEFAULT.deSerialize(dataBytes, requestType);
        return o;
    }

    private static Class<? extends Packet> getRequestType(byte command) {
        // TODO 改造成枚举类
        if (command == Command.LOGIN) {
            return LoginPacket.class;
        } else if (command == Command.RESPONSE) {
            return LoginResponsePacket.class;
        } else if (command == Command.RESPONSE_MSG) {
            return MessagePacket.class;
        }
        return null;
    }

}
