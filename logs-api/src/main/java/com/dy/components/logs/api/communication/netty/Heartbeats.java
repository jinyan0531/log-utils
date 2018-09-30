package com.dy.components.logs.api.communication.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Heartbeats {

    private static final ByteBuf HEARTBEAT_BUF;
    /** 协议头长度 */
    public static final int HEADER_SIZE = 16;
    /** Magic */
    public static final short MAGIC = (short) 0xdada;

    public static final byte HEARTBEAT  = 0x0f;     // Heartbeat

    static {
        ByteBuf buf = Unpooled.buffer(HEADER_SIZE);
        buf.writeShort(MAGIC);
        buf.writeByte(HEARTBEAT); // 心跳包这里可忽略高地址的4位序列化/反序列化标志
        buf.writeByte(0);
        buf.writeLong(0);
        buf.writeInt(0);
        HEARTBEAT_BUF = Unpooled.unreleasableBuffer(buf).asReadOnly();
    }

    /**
     * Returns the shared heartbeat content.
     */
    public static ByteBuf heartbeatContent() {
        return HEARTBEAT_BUF.duplicate();
    }
}
