package com.fb.exceptions.game;

import com.fb.exceptions.BaseException;
import com.fb.messages.ClientBaseMessage;

public class InvalidStateException extends BaseException {

    private String crtState;
    private String[] expectedStates;

    public InvalidStateException(ClientBaseMessage clientMsg, String crtState, String[] expected) {
	super(clientMsg);
	this.crtState = crtState;
	this.expectedStates = expected;
    }

    public String getCrtState() {
	return crtState;
    }

    public String[] getExpectedStates() {
	return expectedStates;
    }

}
