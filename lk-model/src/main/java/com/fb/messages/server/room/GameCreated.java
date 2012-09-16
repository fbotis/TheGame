package com.fb.messages.server.room;

import com.fb.messages.ServerBaseMessage;
import com.fb.topics.Topic;

public class GameCreated extends ServerBaseMessage {
    private String gameName;
    private String gameId;
    private String creatorUserId;

    public GameCreated(String gameName, String gameId, String creatorUserId) {
	super(Topic.ALL_TOPIC);
	this.gameName = gameName;
	this.gameId = gameId;
	this.creatorUserId = creatorUserId;
    }

    public String getCreatorUserId() {
	return creatorUserId;
    }

    public String getGameName() {
	return gameName;
    }

    public String getGameId() {
	return gameId;
    }
}
