package com.fb.messages.server.gameactions;

import com.fb.messages.ServerBaseMessage;

public class BeginChallenge extends ServerBaseMessage {

    private String gameId;
    private String nextPlayerId;

    public BeginChallenge(String gameId, String nextPlayerId) {
	super("S" + gameId);
	this.gameId = gameId;
	this.nextPlayerId = nextPlayerId;
    }

    public String getGameId() {
	return gameId;
    }

    public String getNextPlayerId() {
	return nextPlayerId;
    }

}
