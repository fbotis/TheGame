package com.fb.messages.server.room;

import com.fb.messages.ServerBaseMessage;
import com.fb.topics.Topic;

public class UserJoinedGame extends ServerBaseMessage {

    private String joinedUserId;
    private String joinedGameId;

    public UserJoinedGame(String joinedUserId, String joinedGameId) {
	super(Topic.ALL_TOPIC);
	this.joinedUserId = joinedUserId;
	this.joinedGameId = joinedGameId;
    }

    public String getJoinedGameId() {
	return joinedGameId;
    }

    public String getJoinedUserId() {
	return joinedUserId;
    }
}
