package com.fb.messages.server.gameactions;

import com.fb.messages.ServerBaseMessage;

public class PlayerChallenged extends ServerBaseMessage {
    private String gameId;
    private String challengedPlayerId;
    private String territoryId;
    private String challengerId;

    public PlayerChallenged(String gameId, String challengerId, String challengedPlayerId, String territoryId) {
	super("S" + gameId);
	this.gameId = gameId;
	this.challengerId = challengerId;
	this.challengedPlayerId = challengedPlayerId;
	this.territoryId = territoryId;
    }

    public String getGameId() {
	return gameId;
    }

    public String getChallengedPlayerId() {
	return challengedPlayerId;
    }

    public String getTerritoryId() {
	return territoryId;
    }

    public String getChallengerId() {
	return challengerId;
    }

}
