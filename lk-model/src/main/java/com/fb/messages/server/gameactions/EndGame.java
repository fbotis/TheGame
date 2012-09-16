package com.fb.messages.server.gameactions;

import com.fb.messages.ServerBaseMessage;

public class EndGame extends ServerBaseMessage {
    private String gameId;
    private String winnerPlayerId;

    public EndGame(String gameId, String winnerPlayerId) {
	super("S" + gameId);
	this.gameId = gameId;
	this.winnerPlayerId = winnerPlayerId;
    }

    public String getGameId() {
	return gameId;
    }

    public String getWinnerPlayerId() {
	return winnerPlayerId;
    }

}
