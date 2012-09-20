package com.fb.messages.client.gameactions;

public class ChooseTerritory extends GameActionBaseMessage {
    private String territoryId;

    public ChooseTerritory(String userId, String gameId, String territoryId) {
	super(userId, gameId);
	this.gameId = gameId;
	this.territoryId = territoryId;
    }

    public String getTerritoryId() {
	return territoryId;
    }
}
