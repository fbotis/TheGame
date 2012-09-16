package com.fb.messages.server.general;

import java.util.List;

import com.fb.messages.ServerBaseMessage;

public class Snapshot extends ServerBaseMessage {

    public static class Room {
	private String name;
	private String roomId;
	private List<String> players;
	private String creatorId;

	public Room(String name, String roomId, List<String> players, String creatorId) {
	    this.name = name;
	    this.roomId = roomId;
	    this.players = players;
	    this.creatorId = creatorId;
	}

	public String getName() {
	    return name;
	}

	public String getRoomId() {
	    return roomId;
	}

	public List<String> getPlayers() {
	    return players;
	}

	public String getCreatorId() {
	    return creatorId;
	}

    }

    private List<Room> rooms;

    public Snapshot(String requesterUserId, List<Room> rooms) {
	super(requesterUserId);
	this.rooms = rooms;
    }

    public List<Room> getRooms() {
	return rooms;
    }

}
