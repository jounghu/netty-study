package com.dobest.protocol.packet;

import com.dobest.protocol.Command;
import lombok.Data;

/**
 * @Package: com.dobest.protocol
 * @author: Jounghu
 * @date: 2018/10/21 19:52
 * @version: V1.0
 */
@Data
public class LoginPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN;
    }
}
