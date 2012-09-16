package com.fb.exceptions.game;

import com.fb.exceptions.BaseException;
import com.fb.messages.ClientBaseMessage;

public class PlayerNotInGameException extends BaseException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PlayerNotInGameException(ClientBaseMessage msg) {
	super(msg);
    }
}
