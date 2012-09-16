package com.fb.exceptions.room;

import com.fb.exceptions.BaseException;
import com.fb.messages.client.room.JoinGame;

public class UserAlreadyJoinedException extends BaseException {

    private static final long serialVersionUID = 1L;

    public UserAlreadyJoinedException(JoinGame joinGameMsg) {
	super(joinGameMsg);
    }

}
