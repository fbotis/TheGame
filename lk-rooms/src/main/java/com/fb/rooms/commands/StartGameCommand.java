package com.fb.rooms.commands;

import com.fb.exceptions.room.RoomNotFoundException;
import com.fb.messages.client.room.StartGame;
import com.fb.messages.intercomponents.NewGame;
import com.fb.messages.server.room.GameStarted;
import com.fb.rooms.RoomsManager;
import com.fb.rooms.model.Room;
import com.fb.transport.IServerMessageSender;

@MessageCommand(type = StartGame.class)
public class StartGameCommand extends Command<StartGame> {

    public StartGameCommand(RoomsManager roomsManager, IServerMessageSender serverSender, StartGame startGame) {
	super(roomsManager, serverSender, startGame);
    }

    @Override
    public void doWork() throws RoomNotFoundException {
	Room room = getRoomsManager().startRoom(getMessage());
	// TODO the newGame message should arrive on the game engine before
	// gameStarted to the user
	sendServerMessage(new NewGame(room.getId(), room.getCreatorId(), room.getUsers(), room.getName()));
	sendServerMessage(new GameStarted(getMessage().getGameId()));
    }

}