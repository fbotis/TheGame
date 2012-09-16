package com.fb.game.commands;

import org.apache.log4j.Logger;

import com.fb.exceptions.BaseException;
import com.fb.game.model.GameLogic;
import com.fb.messages.BaseMessage;
import com.fb.messages.ServerBaseMessage;
import com.fb.messages.server.ErrorMessage;
import com.fb.transport.IServerMessageSender;

public abstract class Command<T extends BaseMessage> implements Runnable {

    protected final Logger logger = Logger.getLogger(getClass());

    private GameLogic gameLogic;
    private IServerMessageSender serverSender;
    private T msg;

    public Command(GameLogic gameLogic, IServerMessageSender serverSender, T msg) {
	this.gameLogic = gameLogic;
	this.serverSender = serverSender;
	this.msg = msg;
    }

    public GameLogic getGameLogic() {
	return gameLogic;
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
