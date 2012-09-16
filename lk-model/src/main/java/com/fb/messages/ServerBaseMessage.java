package com.fb.messages;

public class ServerBaseMessage extends BaseMessage {

    public ServerBaseMessage(String... topics) {
	for (String topic : topics) {
	    addTopic(topic);
	}
    }
}
