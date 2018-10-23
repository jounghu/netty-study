package com.dobest.protocol;

/**
 * @Package: com.dobest.protocol
 * @author: Jounghu
 * @date: 2018/10/21 19:51
 * @version: V1.0
 */
public interface Command {

    Byte LOGIN = 1;

    Byte RESPONSE = 2;

    Byte RESPONSE_MSG = 3;

    Byte REQUEST_MSG = 4;

}
