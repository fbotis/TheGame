package com.fb.game.commands;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

import com.fb.game.GamesManager;
import com.fb.game.model.GameLogic;
import com.fb.messages.client.gameactions.GameActionBaseMessage;
import com.fb.transport.IServerMessageSender;

public class CommandFactory {

    private static final Logger logger = Logger.getLogger(CommandFactory.class);
    private static Map<Class<?>, Class<?>> cmdsMap = new HashMap<Class<?>, Class<?>>();

    private IServerMessageSender serverMsgSender;
    private GamesManager gamesManager;

    public CommandFactory(GamesManager gamesService, IServerMessageSender msgSender) {
	this.gamesManager = gamesService;
	this.serverMsgSender = msgSender;
	Reflections reflections = new Reflections("com.fb.game.commands");
	Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(MessageCommand.class);
	for (Class<?> c : annotated) {
	    cmdsMap.put(c.getAnnotation(MessageCommand.class).type(), c);
	}

    }

    public Command<?> getCommand(GameActionBaseMessage clientMsg) {
	Class<? extends Command> cmdClasz = (Class<? extends Command>) cmdsMap.get(clientMsg.getClass());
	Constructor<? extends Command> constr;
	try {
	    constr = cmdClasz.getConstructor(GameLogic.class, IServerMessageSender.class, clientMsg.getClass());
	    Command cmd = constr.newInstance(gamesManager.getGameLogic(clientMsg.getGameId()), serverMsgSender,
		    clientMsg);
	    return cmd;
	} catch (Exception e) {
	    logger.error("Error creating cmd", e);
	    return null;
	}
    }

    public void setServerMsgSender(IServerMessageSender serverMsgSender) {
	this.serverMsgSender = serverMsgSender;
    }
}
