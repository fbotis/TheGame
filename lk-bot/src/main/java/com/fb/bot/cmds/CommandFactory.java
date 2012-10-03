package com.fb.bot.cmds;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

import com.fb.bot.Bot;
import com.fb.messages.BaseMessage;

public class CommandFactory {

    private static final Logger logger = Logger.getLogger(CommandFactory.class);
    private static Map<Class<?>, Class<?>> cmdsMap = new HashMap<Class<?>, Class<?>>();
    private Bot bot;

    public CommandFactory(Bot bot) {
	this.bot = bot;
	Reflections reflections = new Reflections("com.fb.bot.cmds.server.room","com.fb.bot.cmds.server.game");
	Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(MessageCommand.class);
	for (Class<?> c : annotated) {
	    cmdsMap.put(c.getAnnotation(MessageCommand.class).type(), c);
	}

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Command<?> getCommand(BaseMessage msgFromServer) {
	Class<? extends Command> cmdClasz = (Class<? extends Command>) cmdsMap.get(msgFromServer.getClass());
	Constructor<? extends Command> constr;
	try {
	    constr = cmdClasz.getConstructor(Bot.class, msgFromServer.getClass());
	    Command cmd = constr.newInstance(bot, msgFromServer);
	    return cmd;
	} catch (Exception e) {
	    logger.error("Error creating cmd", e);
	    return null;
	}
    }

}
