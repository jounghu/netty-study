package com.dobest.protocol.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @Package: com.dobest.protocol.serialize
 * @author: Jounghu
 * @date: 2018/10/21 19:58
 * @version: V1.0
 */
public class JSONSerializer<T> implements Serializer<T> {

    @Override
    public byte getSerializer() {
        return SerializerAlg.JSON;
    }

    @Override
    public byte[] serialize(Object obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public T deSerialize(byte[] source, Class<T> clazz) {
        return JSON.parseObject(source, clazz);
    }

}
