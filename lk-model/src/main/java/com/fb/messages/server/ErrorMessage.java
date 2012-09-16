package com.fb.messages.server;

import com.fb.exceptions.BaseException;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.ServerBaseMessage;

public class ErrorMessage extends ServerBaseMessage {

    private ClientBaseMessage clientMsg;
    private String errorType;

    public ErrorMessage(BaseException ex) {
	addTopic(ex.getClientMsg().getUserId());
	this.clientMsg = ex.getClientMsg();
	this.errorType = ex.getErrorType();
    }

    public ClientBaseMessage getClientMsg() {
	return clientMsg;
    }

    public String getErrorType() {
	return errorType;
    }
}
