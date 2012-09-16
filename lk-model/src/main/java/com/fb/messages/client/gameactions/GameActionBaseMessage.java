package com.fb.messages.client.gameactions;

import com.fb.messages.ClientBaseMessage;

public class GameActionBaseMessage extends ClientBaseMessage {

    public GameActionBaseMessage(String userId, String... topics) {
	super(userId, topics);
    }

    protected String gameId;

    public String getGameId() {
	return gameId;
    }
}
