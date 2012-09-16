package com.fb.exceptions.game;

import com.fb.exceptions.BaseException;
import com.fb.messages.ClientBaseMessage;

public class ChallengeInProgressException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ChallengeInProgressException(ClientBaseMessage clientMsg) {
	super(clientMsg);
    }

}
