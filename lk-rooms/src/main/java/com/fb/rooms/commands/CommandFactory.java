package com.fb.rooms.commands;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

import com.fb.messages.ClientBaseMessage;
import com.fb.rooms.RoomsManager;
import com.fb.transport.IServerMessageSender;

public class CommandFactory {

    private static final Logger logger = Logger.getLogger(CommandFactory.class);
    private static Map<Class<?>, Class<?>> cmdsMap = new HashMap<Class<?>, Class<?>>();

    private IServerMessageSender serverMsgSender;
    private RoomsManager roomsManager;

    public CommandFactory(RoomsManager roomsManager, IServerMessageSender msgSender) {
	this.roomsManager = roomsManager;
	this.serverMsgSender = msgSender;
	Reflections reflections = new Reflections("com.fb.rooms.commands");
	Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(MessageCommand.class);
	for (Class<?> c : annotated) {
	    cmdsMap.put(c.getAnnotation(MessageCommand.class).type(), c);
	}

    }

    public Command<?> getCommand(ClientBaseMessage clientMsg) {
	Class<? extends Command> cmdClasz = (Class<? extends Command>) cmdsMap.get(clientMsg.getClass());
	Constructor<? extends Command> constr;
	try {
	    constr = cmdClasz.getConstructor(RoomsManager.class, IServerMessageSender.class, clientMsg.getClass());
	    Command cmd = constr.newInstance(roomsManager, serverMsgSender, clientMsg);
	    return cmd;
	} catch (Exception e) {
	    logger.error("Error creating cmd", e);
	    return null;
	}
    }

    public void setRoomsManager(RoomsManager roomsManager) {
	this.roomsManager = roomsManager;
    }

    public void setServerMsgSender(IServerMessageSender serverMsgSender) {
	this.serverMsgSender = serverMsgSender;
    }
}
