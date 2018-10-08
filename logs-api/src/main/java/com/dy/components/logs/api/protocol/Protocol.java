package com.dy.components.logs.api.protocol;

import com.dy.components.logs.api.communication.RegeditMeta;

public class Protocol {
    ProtocolEnum type;

    RegeditMeta regeditMeta;



    public ProtocolEnum getType() {
        return type;
    }

    public void setType(ProtocolEnum type) {
        this.type = type;
    }
}
