package com.fb.rooms.commands;

import com.fb.exceptions.room.RoomNotFoundException;
import com.fb.exceptions.room.UserAlreadyUnJoinedException;
import com.fb.messages.client.room.UnjoinGame;
import com.fb.messages.server.room.UserUnjoinedGame;
import com.fb.rooms.RoomsManager;
import com.fb.transport.IServerMessageSender;

@MessageCommand(type = UnjoinGame.class)
public class UnjoinGameCommand extends Command<UnjoinGame> {

    public UnjoinGameCommand(RoomsManager roomsManager, IServerMessageSender serverSender, UnjoinGame unjoinGame) {
	super(roomsManager, serverSender, unjoinGame);
    }

    @Override
    public void doWork() throws RoomNotFoundException, UserAlreadyUnJoinedException {
	getRoomsManager().unjoinRoom(getMessage());
	sendServerMessage(new UserUnjoinedGame(getMessage().getUserId(), getMessage().getGameId()));
    }

}
