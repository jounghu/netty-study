package com.dobest.protocol.packet;

import com.dobest.protocol.Command;
import lombok.Data;

/**
 * @author: hujiansong
 * @since: 2018/10/23 10:39
 */
@Data
public class SendMessagePacket extends Packet {
    String respMsg;

    String fromId;


    @Override
    public Byte getCommand() {
        return Command.RESPONSE_MSG;
    }
}
