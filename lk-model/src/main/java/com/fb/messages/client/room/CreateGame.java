package com.fb.messages.client.room;

import com.fb.messages.ClientBaseMessage;
import com.fb.topics.Topic;

public class CreateGame extends ClientBaseMessage {

    private String gameName;

    public CreateGame(String userId, String gameName) {
	super(userId, Topic.ROOMS_ENGINE);
	this.gameName = gameName;
    }

    public String getGameName() {
	return gameName;
    }

}
