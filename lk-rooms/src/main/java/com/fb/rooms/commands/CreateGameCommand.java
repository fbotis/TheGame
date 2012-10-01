package com.fb.rooms.commands;

import com.fb.exceptions.room.UserAlreadyCreatedGameException;
import com.fb.messages.client.room.CreateGame;
import com.fb.messages.server.room.GameCreated;
import com.fb.rooms.RoomsManager;
import com.fb.rooms.model.Room;
import com.fb.transport.IServerMessageSender;

@MessageCommand(type = CreateGame.class)
public class CreateGameCommand extends Command<CreateGame> {

    public CreateGameCommand(RoomsManager roomsManager, IServerMessageSender serverSender, CreateGame createGameMsg) {
	super(roomsManager, serverSender, createGameMsg);
    }

    @Override
    public void doWork() throws UserAlreadyCreatedGameException {
	Room room = getRoomsManager().createRoom(getMessage());
	sendServerMessage(new GameCreated(room.getName(), room.getId(), room.getCreatorId()));
    }
}
