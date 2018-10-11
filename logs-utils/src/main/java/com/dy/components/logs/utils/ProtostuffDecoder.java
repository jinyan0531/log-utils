package com.dy.components.logs.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ProtostuffDecoder extends ByteToMessageDecoder {

    //抽象
    Class clazz;

    public ProtostuffDecoder(Class clazz){
        this.clazz = clazz;
    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        Object obj = ProtostuffUtil.deserializer(data, clazz);
        out.add(obj);
    }
}
