package com.dobest.protocol.serialize;

/**
 * @Package: com.dobest.protocol
 * @author: Jounghu
 * @date: 2018/10/21 19:54
 * @version: V1.0
 */
public interface Serializer<T> {


    Serializer DEFAULT = new JSONSerializer();

    /**
     * 获取序列化算法
     *
     * @return
     */
    byte getSerializer();

    /**
     * java对象序列化成二进制
     *
     * @param obj
     * @return
     */
    byte[] serialize(T obj);

    /**
     * 二进制序列化成java对象
     *
     * @param source
     * @param clazz
     * @return
     */
    T deSerialize(byte[] source, Class<T> clazz);

}
