package com.dy.components.logs.api.communication;

import java.io.IOException;

public interface IConfirmListener {
    void handleOk()throws IOException;

    void handleFail()throws IOException;
}
