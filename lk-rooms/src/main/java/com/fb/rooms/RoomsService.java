package com.fb.rooms;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.fb.messages.ClientBaseMessage;
import com.fb.messages.ServerBaseMessage;
import com.fb.rooms.commands.Command;
import com.fb.rooms.commands.CommandFactory;
import com.fb.transport.IMessageHandler;
import com.fb.transport.IServerMessageSender;

public class RoomsService implements IMessageHandler {

    // TODO make it configurable
    private static final Integer THREAD_POOL_SIZE = new Integer(10);

    private IServerMessageSender msgSender;
    private RoomsManager roomsManager;
    private Executor threadPool;
    private CommandFactory cmdFactory;

    public RoomsService(IServerMessageSender msgSender) {
	this.msgSender = msgSender;
	this.roomsManager = new RoomsManager();
	threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    @Override
    public void handleClientMessage(ClientBaseMessage msg) {
	if (cmdFactory == null) {
	    cmdFactory = new CommandFactory(roomsManager, msgSender);
	}
	Command cmd = cmdFactory.getCommand(msg);
	threadPool.execute(cmd);
    }

    @Override
    public void handleServerMessage(ServerBaseMessage arg0) {

    }

}
