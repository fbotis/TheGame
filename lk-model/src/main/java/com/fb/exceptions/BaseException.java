package com.fb.exceptions;

import com.fb.messages.ClientBaseMessage;

public abstract class BaseException extends Exception {

    protected ClientBaseMessage clientMsg;

    public BaseException(ClientBaseMessage clientMsg) {
	this.clientMsg = clientMsg;
    }

    public String getErrorType() {
	return this.getClass().getName();
    }

    public ClientBaseMessage getClientMsg() {
	return clientMsg;
    }
}
