package com.fb.exceptions.game;

import com.fb.exceptions.BaseException;
import com.fb.messages.ClientBaseMessage;

public class InvalidUserException extends BaseException{

    public InvalidUserException(ClientBaseMessage clientMsg) {
	super(clientMsg);
	// TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
