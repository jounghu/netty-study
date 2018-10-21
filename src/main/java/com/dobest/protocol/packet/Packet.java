package com.dobest.protocol.packet;

import lombok.Data;

/**
 * @Package: com.dobest.protocol
 * @author: Jounghu
 * @date: 2018/10/21 19:49
 * @version: V1.0
 */
@Data
public abstract class Packet {


    private Byte version = 1;

    /**
     * 获取指令
     *
     * @return
     */
    public abstract Byte getCommand();
}
