package com.dobest.protocol.packet;

import com.dobest.protocol.Command;
import lombok.Data;

/**
 * @author: hujiansong
 * @since: 2018/10/23 14:30
 */
@Data
public class ReciveMessagePacket extends Packet {

    String reciveMsg;

    String fromUserId;

    String fromUsername;

    @Override
    public Byte getCommand() {
        return Command.RECIVE_MSG;
    }
}
