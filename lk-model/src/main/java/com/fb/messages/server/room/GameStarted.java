package com.fb.messages.server.room;

import java.util.List;

import com.fb.messages.ServerBaseMessage;
import com.fb.topics.Topic;

public class GameStarted extends ServerBaseMessage {

    private String gameId;
    private List<String> map;

    public GameStarted(String gameId, List<String> map) {
	super(Topic.ALL_TOPIC);
	this.gameId = gameId;
	this.map = map;
    }

    public String getGameId() {
	return gameId;
    }

    public List<String> getMap() {
	return map;
    }
}
