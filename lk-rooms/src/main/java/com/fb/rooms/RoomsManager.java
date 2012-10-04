package com.fb.rooms;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fb.exceptions.room.RoomNotFoundException;
import com.fb.exceptions.room.UserAlreadyCreatedGameException;
import com.fb.exceptions.room.UserAlreadyJoinedException;
import com.fb.exceptions.room.UserAlreadyUnJoinedException;
import com.fb.messages.client.ClientDisconnected;
import com.fb.messages.client.room.CreateGame;
import com.fb.messages.client.room.JoinGame;
import com.fb.messages.client.room.StartGame;
import com.fb.messages.client.room.UnjoinGame;
import com.fb.rooms.model.Room;

public class RoomsManager {
    private Map<String, Room> roomsByName = new ConcurrentHashMap<String, Room>();
    private Map<String, Room> roomsByCreatorId = new ConcurrentHashMap<String, Room>();
    private Map<String, Room> roomsByRoomId = new ConcurrentHashMap<String, Room>();

    public Room createRoom(CreateGame createGame) throws UserAlreadyCreatedGameException {
	String roomName = createGame.getGameName();
	if (roomsByName.get(roomName) != null) {
	    // TODO change this to user name instead of user id
	    roomName += createGame.getUserId();
	}

	if (roomsByCreatorId.get(createGame.getUserId()) != null) {
	    throw new UserAlreadyCreatedGameException(createGame);
	}

	Room room = new Room(createGame.getUserId(), roomName);
	putRoom(room);

	return room;
    }

    public Room joinRoom(JoinGame joinGameMsg) throws RoomNotFoundException, UserAlreadyJoinedException {
	String userId = joinGameMsg.getUserId();
	String gameId = joinGameMsg.getGameId();

	Room room = null;
	if ((room = roomsByRoomId.get(gameId)) == null) {
	    throw new RoomNotFoundException(joinGameMsg);
	}

	if (room.getUsers().contains(userId)) {
	    throw new UserAlreadyJoinedException(joinGameMsg);
	}

	room.getUsers().add(userId);
	return room;
    }

    public Room unjoinRoom(UnjoinGame unjoinGame) throws RoomNotFoundException, UserAlreadyUnJoinedException {
	String userId = unjoinGame.getUserId();
	String gameId = unjoinGame.getGameId();
	Room room = null;

	if ((room = roomsByRoomId.get(gameId)) == null) {
	    throw new RoomNotFoundException(unjoinGame);
	}

	if (!room.getUsers().contains(userId)) {
	    throw new UserAlreadyUnJoinedException(unjoinGame);
	}

	room.getUsers().remove(userId);

	return room;
    }

    public Room startRoom(StartGame startGame) throws RoomNotFoundException {
	String gameId = startGame.getGameId();
	Room room = null;

	if ((room = roomsByRoomId.get(gameId)) == null) {
	    throw new RoomNotFoundException(startGame);
	}
	
	room=removeRoom(room);
	room.initializeMap();
	return room;
    }

    public Map<String, Room> getAllRoomsByRoomId() {
	return Collections.unmodifiableMap(roomsByRoomId);
    }

    private Room removeRoom(Room room) {
	roomsByCreatorId.remove(room.getCreatorId());
	roomsByName.remove(room.getName());
	return roomsByRoomId.remove(room.getId());
    }

    private void putRoom(Room room) {
	roomsByCreatorId.put(room.getCreatorId(), room);
	roomsByName.put(room.getName(), room);
	roomsByRoomId.put(room.getId(), room);
    }

    public void userDisconnected(ClientDisconnected clientDisconnectedMsg) throws RoomNotFoundException,
	    UserAlreadyUnJoinedException {
	// TODO is ok this?it;s enough?
	for (Room r : roomsByCreatorId.values()) {
	    if (r.getUsers().contains(clientDisconnectedMsg.getClientId())) {
		unjoinRoom(new UnjoinGame(clientDisconnectedMsg.getClientId(), r.getId()));
		return;
	    }
	}
    }
}
