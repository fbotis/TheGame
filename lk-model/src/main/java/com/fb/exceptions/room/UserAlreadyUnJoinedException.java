package com.fb.exceptions.room;

import com.fb.exceptions.BaseException;
import com.fb.messages.client.room.UnjoinGame;

public class UserAlreadyUnJoinedException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public UserAlreadyUnJoinedException(UnjoinGame unjoinGame) {
	super(unjoinGame);
    }

}
