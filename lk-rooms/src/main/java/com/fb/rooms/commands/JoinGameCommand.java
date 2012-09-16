package com.fb.rooms.commands;

import com.fb.exceptions.room.RoomNotFoundException;
import com.fb.exceptions.room.UserAlreadyJoinedException;
import com.fb.messages.client.room.JoinGame;
import com.fb.messages.server.room.UserJoinedGame;
import com.fb.rooms.RoomsManager;
import com.fb.transport.IServerMessageSender;

@MessageCommand(type = JoinGame.class)
public class JoinGameCommand extends Command<JoinGame> {

    public JoinGameCommand(RoomsManager roomsManager, IServerMessageSender serverSender, JoinGame joinGame) {
	super(roomsManager, serverSender, joinGame);
    }

    @Override
    public void doWork() throws RoomNotFoundException, UserAlreadyJoinedException {
	getRoomsManager().joinRoom(getMessage());
	sendServerMessage(new UserJoinedGame(getMessage().getUserId(), getMessage().getGameId()));
    }

}
