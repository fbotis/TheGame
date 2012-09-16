package com.fb.messages.server.gameactions;

import com.fb.messages.ServerBaseMessage;

public class TerritoryChosen extends ServerBaseMessage {
    private String gameId;
    private String territoryUserId;
    private String territoryId;

    public TerritoryChosen(String gameId, String territoryUserId, String territoryId) {
	super("S" + gameId);
	this.gameId = gameId;
	this.territoryUserId = territoryUserId;
	this.territoryId = territoryId;
    }

    public String getGameId() {
	return gameId;
    }

    public String getTerritoryUserId() {
	return territoryUserId;
    }

    public String getTerritoryId() {
	return territoryId;
    }

}
