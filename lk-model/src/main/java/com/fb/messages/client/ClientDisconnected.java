package com.fb.messages.client;

import com.fb.messages.ClientBaseMessage;
import com.fb.topics.Topic;

public class ClientDisconnected extends ClientBaseMessage {

    private String clientId;

    public ClientDisconnected(String clientId) {
	super(clientId, Topic.CLIENT_DISCONNECTED);
	this.clientId = clientId;
    }

    public String getClientId() {
	return clientId;
    }
}
