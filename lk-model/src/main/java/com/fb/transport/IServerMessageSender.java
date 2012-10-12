package com.fb.transport;

import com.fb.messages.ServerBaseMessage;

public interface IServerMessageSender {

    void sendServerMessage(ServerBaseMessage msg);

    public void subscribeToTopic(String topic) throws Exception;

    public void unsubscribeFromTopic(String topic) throws Exception;
    
    public boolean isConnected();

}
