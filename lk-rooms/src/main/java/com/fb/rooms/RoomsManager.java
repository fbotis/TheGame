package com.fb.rooms;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fb.exceptions.room.DuplicateRoomException;
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

    public Room createRoom(CreateGame createGame) throws DuplicateRoomException, UserAlreadyCreatedGameException {
	if (roomsByName.get(createGame.getGameName()) != null) {
	    throw new DuplicateRoomException(createGame);
	}

	if (roomsByCreatorId.get(createGame.getUserId()) != null) {
	    throw new UserAlreadyCreatedGameException(createGame);
	}

	Room room = new Room(createGame.getUserId(), createGame.getGameName());
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

	return removeRoom(room);
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
	for (Room r : roomsByCreatorId.values()) {
	    if (r.getUsers().contains(clientDisconnectedMsg.getClientId())) {
		unjoinRoom(new UnjoinGame(clientDisconnectedMsg.getClientId(), r.getId()));
		return;
	    }
	}
    }
}
