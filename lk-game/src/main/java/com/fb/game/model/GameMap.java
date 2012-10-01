package com.fb.game.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameMap {
    private static final String FREE_TERRITORY = "free";

    private Map<String, String> territoriesIds = new HashMap<String, String>();

    public GameMap(List<String> mapIds) {
	for (String id : mapIds) {
	    territoriesIds.put(id, FREE_TERRITORY);
	}
    }

    public synchronized void assignTerritory(String territoryId, String userId) {
	territoriesIds.put(territoryId, userId);
    }

    public synchronized boolean isAnyTerritoryFree() {
	return territoriesIds.values().contains(FREE_TERRITORY);
    }

    public synchronized boolean isAnyWinner() {
	if (isAnyTerritoryFree())
	    return false;
	Set<String> winnersSet = new HashSet<String>();
	winnersSet.addAll(territoriesIds.values());
	return winnersSet.size() == 1;
    }

    public synchronized boolean isAssigned(String territoryId) {
	if (territoriesIds.get(territoryId).equals(FREE_TERRITORY)) {
	    return false;
	}
	return true;
    }

    public String getOwnerId(String territoryId) {
	return territoriesIds.get(territoryId);
    }

}
