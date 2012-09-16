package com.fb.messages.client.gameactions;

public class ChallengePlayer extends GameActionBaseMessage {

    private String challengedPlayerId;
    private String territoryId;

    public ChallengePlayer(String userId, String gameId, String challengedPlayerId, String territoryId) {
	super(userId, gameId);
	this.gameId = gameId;
	this.challengedPlayerId = challengedPlayerId;
	this.territoryId = territoryId;
    }

    public String getChallengedPlayerId() {
	return challengedPlayerId;
    }

    public String getTerritoryId() {
	return territoryId;
    }

}
