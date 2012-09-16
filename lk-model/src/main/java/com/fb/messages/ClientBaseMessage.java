package com.fb.messages;

public class ClientBaseMessage extends BaseMessage {

    protected String userId;

    public ClientBaseMessage(String userId, String... topics) {
	this.userId = userId;
	for (String topic : topics) {
	    addTopic(topic);
	}
    }

    public String getUserId() {
	return userId;
    }

}
