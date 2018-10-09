package com.dy.components.logs.api.protocol;

import com.dy.components.logs.api.communication.RegisterMeta;

import java.io.Serializable;

public class Message  implements Serializable {
    ProtocolEnum type;

    RegisterMeta regeditMeta;



    Content content;


    public ProtocolEnum getType() {
        return type;
    }

    public void setType(ProtocolEnum type) {
        this.type = type;
    }



    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public RegisterMeta getRegeditMeta() {
        return regeditMeta;
    }

    public void setRegeditMeta(RegisterMeta regeditMeta) {
        this.regeditMeta = regeditMeta;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", regeditMeta=" + regeditMeta +
                ", content=" + content +
                '}';
    }
}
