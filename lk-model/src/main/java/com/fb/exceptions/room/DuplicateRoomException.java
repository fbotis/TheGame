package com.fb.exceptions.room;

import com.fb.exceptions.BaseException;
import com.fb.messages.client.room.CreateGame;

public class DuplicateRoomException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private CreateGame createGameMsg;

    public DuplicateRoomException(CreateGame createGame) {
	super(createGame);
	this.createGameMsg = createGame;
    }

    public CreateGame getCreateGameMsg() {
	return createGameMsg;
    }
}
