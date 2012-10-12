package com.fb.messages.server.general;

import java.io.Serializable;
import java.util.List;

import com.fb.messages.ServerBaseMessage;

public class Snapshot extends ServerBaseMessage {

    public static class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6496857422532242133L;
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

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((creatorId == null) ? 0 : creatorId.hashCode());
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
	    result = prime * result + ((players == null) ? 0 : players.hashCode());
	    result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (getClass() != obj.getClass())
		return false;
	    Room other = (Room) obj;
	    if (creatorId == null) {
		if (other.creatorId != null)
		    return false;
	    } else if (!creatorId.equals(other.creatorId))
		return false;
	    if (name == null) {
		if (other.name != null)
		    return false;
	    } else if (!name.equals(other.name))
		return false;
	    if (players == null) {
		if (other.players != null)
		    return false;
	    } else if (!players.equals(other.players))
		return false;
	    if (roomId == null) {
		if (other.roomId != null)
		    return false;
	    } else if (!roomId.equals(other.roomId))
		return false;
	    return true;
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
