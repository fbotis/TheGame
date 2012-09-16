package com.fb.exceptions.game;

import com.fb.exceptions.BaseException;
import com.fb.messages.ClientBaseMessage;

public class InvalidGameException extends BaseException {

    public InvalidGameException(ClientBaseMessage clientMsg) {
	super(clientMsg);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
