package com.dy.components.logs.api.protocol;

import com.dy.components.logs.api.communication.RegeditMeta;
import com.dy.components.logs.api.log.collectlog.DefaultCollectLog;


/**
 * @author fufeijian
 */
public class RegeditProtocol extends Protocol{

    RegeditMeta regeditMeta;



    public RegeditMeta getRegeditMeta() {
        return regeditMeta;
    }

    public void setRegeditMeta(RegeditMeta regeditMeta) {
        this.regeditMeta = regeditMeta;
    }


}
