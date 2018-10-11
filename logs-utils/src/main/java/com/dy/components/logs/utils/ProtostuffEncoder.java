package com.dy.components.logs.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtostuffEncoder extends MessageToByteEncoder {



    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
            byte[] data = ProtostuffUtil.serializer(in);
            out.writeInt(data.length);
            out.writeBytes(data);

    }
}
