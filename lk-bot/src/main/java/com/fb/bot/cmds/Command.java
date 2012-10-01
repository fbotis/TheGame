package com.fb.bot.cmds;

import org.apache.log4j.Logger;

import com.fb.bot.Bot;
import com.fb.messages.BaseMessage;

public abstract class Command<T extends BaseMessage> {

    protected final Logger logger = Logger.getLogger(getClass());

    private Bot bot;
    private T msg;

    public Command(Bot bot, T msg) {
	this.bot = bot;
	this.msg = msg;
    }

    public T getMessage() {
	return msg;
    }

    public Bot getBot() {
	return bot;
    }

    public abstract Object execute() throws Exception;
}
