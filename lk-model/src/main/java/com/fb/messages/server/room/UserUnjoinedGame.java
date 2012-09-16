package com.fb.messages.server.room;

import com.fb.messages.ServerBaseMessage;
import com.fb.topics.Topic;

public class UserUnjoinedGame extends ServerBaseMessage {
    private String unjoinedUserId;
    private String unjoinedGameId;

    public UserUnjoinedGame(String unjoinedUserId, String unjoinedGameId) {
	super(Topic.ALL_TOPIC);
	this.unjoinedUserId = unjoinedUserId;
	this.unjoinedGameId = unjoinedGameId;
    }

    public String getUnjoinedGameId() {
	return unjoinedGameId;
    }

    public String getUnjoinedUserId() {
	return unjoinedUserId;
    }
}
