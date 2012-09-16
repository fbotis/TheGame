package com.fb.transport;

import com.fb.messages.ClientBaseMessage;
import com.fb.messages.ServerBaseMessage;

public interface IMessageHandler {

    void handleClientMessage(ClientBaseMessage message);

    void handleServerMessage(ServerBaseMessage message);

}
