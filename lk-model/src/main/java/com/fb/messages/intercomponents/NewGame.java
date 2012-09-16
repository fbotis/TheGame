package com.fb.messages.intercomponents;

import java.util.List;

import com.fb.messages.ServerBaseMessage;
import com.fb.topics.Topic;

public class NewGame extends ServerBaseMessage {
    private String roomId;
    private String roomCreatorId;
    private List<String> roomUsers;
    private String roomName;

    public NewGame(String roomId, String roomCreatorId, List<String> roomUsers, String roomName) {
	addTopic(Topic.INTER_COMPONENT_GAME_ENGINE);
	this.roomId = roomId;
	this.roomCreatorId = roomCreatorId;
	this.roomUsers = roomUsers;
	this.roomName = roomName;
    }

    public String getRoomId() {
	return roomId;
    }

    public String getRoomCreatorId() {
	return roomCreatorId;
    }

    public List<String> getRoomUsers() {
	return roomUsers;
    }

    public String getRoomName() {
	return roomName;
    }

}
