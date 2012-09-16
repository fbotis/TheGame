package com.fb.transport;

import com.fb.messages.ClientBaseMessage;
import com.fb.messages.ServerBaseMessage;

public interface IClientMessageSender {
    void sendClientMessage(ClientBaseMessage msg);
}
