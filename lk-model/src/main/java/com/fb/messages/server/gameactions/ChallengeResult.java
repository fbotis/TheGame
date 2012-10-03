package com.fb.messages.server.gameactions;

import com.fb.messages.ServerBaseMessage;

public class ChallengeResult extends ServerBaseMessage {
    private String gameId;
    private String winnerPlayerId;
    private String territoryId;

    public ChallengeResult(String gameId, String winnerPlayerId, String territoryId) {
	super("S" + gameId);
	this.gameId = gameId;
	this.winnerPlayerId = winnerPlayerId;
	this.territoryId = territoryId;
    }

    public String getGameId() {
	return gameId;
    }

    public String getWinnerPlayerId() {
	return winnerPlayerId;
    }

    public String getTerritoryId() {
	return territoryId;
    }

}
