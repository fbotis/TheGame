package com.fb.android.game;

import java.util.HashMap;
import java.util.Map;

import com.fb.messages.server.general.Snapshot.Room;

public class AndroidGameLogic {
    private Map<String, String> map = new HashMap<String, String>();
    private Room room;

    public AndroidGameLogic(Room room, String[] map2) {
	this.room = room;
	for (String terrId : map2) {
	    map.put(terrId, "FREE");
	}
    }

    public void assignTerritory(String territoryId, String clientId) {
	this.map.put(territoryId, clientId);
    }

    public String getOwner(String territoryId) {
	return map.get(territoryId);
    }

    public boolean isFree(String terrId) {
	return "FREE".equals(map.get(terrId));
    }

}
