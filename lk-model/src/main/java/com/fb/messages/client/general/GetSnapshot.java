package com.fb.messages.client.general;

import com.fb.messages.ClientBaseMessage;
import com.fb.topics.Topic;

public class GetSnapshot extends ClientBaseMessage {

    public GetSnapshot(String userId) {
	super(userId, Topic.ROOMS_ENGINE);
    }

}
