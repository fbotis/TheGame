package com.fb.messages.server.room;

import com.fb.messages.ServerBaseMessage;
import com.fb.topics.Topic;

public class GameStarted extends ServerBaseMessage {

    private String gameId;

    public GameStarted(String gameId) {
	super(Topic.ALL_TOPIC);
	this.gameId = gameId;
    }

    public String getGameId() {
	return gameId;
    }
}
