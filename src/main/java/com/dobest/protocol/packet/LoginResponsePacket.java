package com.dobest.protocol.packet;

import com.dobest.protocol.Command;
import lombok.Data;

/**
 * @Package: com.dobest.protocol.packet
 * @author: Jounghu
 * @date: 2018/10/21 20:42
 * @version: V1.0
 */

@Data
public class LoginResponsePacket extends Packet {

    private String message;

    boolean hasSuccess;

    @Override
    public Byte getCommand() {
        return Command.RESPONSE;
    }
}
