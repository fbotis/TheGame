package com.fb.rooms.commands;

import org.apache.log4j.Logger;

import com.fb.exceptions.BaseException;
import com.fb.messages.BaseMessage;
import com.fb.messages.ServerBaseMessage;
import com.fb.messages.server.ErrorMessage;
import com.fb.rooms.RoomsManager;
import com.fb.transport.IServerMessageSender;

public abstract class Command<T extends BaseMessage> implements Runnable {

    protected final Logger logger = Logger.getLogger(getClass());

    private RoomsManager roomsManager;
    private IServerMessageSender serverSender;
    private T msg;

    public Command(RoomsManager roomsManager, IServerMessageSender serverSender, T msg) {
	this.roomsManager = roomsManager;
	this.serverSender = serverSender;
	this.msg = msg;
    }

    public RoomsManager getRoomsManager() {
	return roomsManager;
    }

    public void sendServerMessage(ServerBaseMessage msg) {
	serverSender.sendServerMessage(msg);
    }

    public T getMessage() {
	return msg;
    }

    public void run() {
	try {
	    doWork();
	} catch (BaseException e) {
	    serverSender.sendServerMessage(new ErrorMessage(e));
	} catch (Throwable e) {
	    logger.error("This isn't expected!!!", e);
	}
    }

    public abstract void doWork() throws Exception;
}
