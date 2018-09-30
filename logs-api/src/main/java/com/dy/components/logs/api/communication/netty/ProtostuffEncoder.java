package com.dy.components.logs.api.communication.netty;

import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;
import com.dy.components.logs.utils.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtostuffEncoder extends MessageToByteEncoder {
    private Class<? extends  DefaultCollectLog> genericClass;

    public ProtostuffEncoder(Class<? extends  DefaultCollectLog> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = ProtostuffUtil.serializer(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
