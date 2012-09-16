package com.fb.exceptions.room;

import com.fb.exceptions.BaseException;
import com.fb.messages.client.room.JoinGame;
import com.fb.messages.client.room.StartGame;
import com.fb.messages.client.room.UnjoinGame;

public class RoomNotFoundException extends BaseException {
    /**
     * 
     */

    private static final long serialVersionUID = 1L;

    public RoomNotFoundException(JoinGame joinGameMsg) {
	super(joinGameMsg);
    }

    public RoomNotFoundException(UnjoinGame unjoinGame) {
	super(unjoinGame);
    }

    public RoomNotFoundException(StartGame startGame) {
	super(startGame);
    }
}
