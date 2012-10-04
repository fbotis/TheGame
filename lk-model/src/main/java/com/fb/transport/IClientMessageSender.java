package com.fb.transport;

import com.fb.messages.ClientBaseMessage;

public interface IClientMessageSender {
    void sendClientMessage(ClientBaseMessage msg);
}
