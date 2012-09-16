package com.fb.exceptions.room;

import com.fb.exceptions.BaseException;
import com.fb.messages.client.room.CreateGame;

public class UserAlreadyCreatedGameException extends BaseException {

    public UserAlreadyCreatedGameException(CreateGame createGame) {
	super(createGame);
    }

}
