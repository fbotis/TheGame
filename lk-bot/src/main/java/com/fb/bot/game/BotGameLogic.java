package com.fb.bot.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fb.bot.Bot;
import com.fb.messages.server.general.Snapshot.Room;

public class BotGameLogic {

    private Bot bot;
    private Map<String, String> map = new HashMap<String, String>();

    public BotGameLogic(Bot bot, Room room, List<String> map) {
	this.bot=bot;
	for (String territory : map) {
	    this.map.put(territory, "FREE");
	}
    }

    public void endGame() {
	// TODO Auto-generated method stub
	// calls when EndGame message is received from server
    }

    public void assignTerritory(String territoryId, String clientId) {
	this.map.put(territoryId, clientId);
    }

    public String nextTerritoryChallenge() {
	for (Entry<String, String> entry : map.entrySet()) {
	    if (!entry.getValue().equals(bot.getClientId())) {
		return entry.getKey();
	    }
	}
	return null;
    }

    public String getOwner(String territoryId) {
	return map.get(territoryId);
    }

    public String nextTerritoryForChoose() {
	for (Entry<String, String> entry : map.entrySet()) {
	    if (entry.getValue().equals("FREE")) {
		return entry.getKey();
	    }
	}
	return null;
    }
}
