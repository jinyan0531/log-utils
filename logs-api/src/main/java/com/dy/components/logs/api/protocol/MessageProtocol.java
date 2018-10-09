package com.dy.components.logs.api.protocol;

import com.dy.components.logs.api.communication.RegeditMeta;

public class MessageProtocol {
    ProtocolEnum type;

    RegeditMeta regeditMeta;



    Content content;


    public ProtocolEnum getType() {
        return type;
    }

    public void setType(ProtocolEnum type) {
        this.type = type;
    }

    public RegeditMeta getRegeditMeta() {
        return regeditMeta;
    }

    public void setRegeditMeta(RegeditMeta regeditMeta) {
        this.regeditMeta = regeditMeta;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageProtocol{" +
                "type=" + type +
                ", regeditMeta=" + regeditMeta +
                ", content=" + content +
                '}';
    }
}
