package com.dobest.protocol;

import com.dobest.protocol.codec.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author: hujiansong
 * @since: 2018/10/23 13:24
 */
public class Spiliter extends LengthFieldBasedFrameDecoder {

    // MAGIC(4) + VERSION(1) + COMMAND(1) + ALG_NAME(1)

    private static final int LENGTH_FIELD_OFFSET = 7;

    // DATA_LENGTH(4)

    private static final int LENGTH_DATA_LENGTH = 4;


    public Spiliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_DATA_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGINC_NUM) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
