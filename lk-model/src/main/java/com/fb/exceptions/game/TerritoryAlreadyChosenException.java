package com.fb.exceptions.game;

import com.fb.exceptions.BaseException;
import com.fb.messages.ClientBaseMessage;

public class TerritoryAlreadyChosenException extends BaseException {

    public TerritoryAlreadyChosenException(ClientBaseMessage msg) {
	super(msg);
    }
}
