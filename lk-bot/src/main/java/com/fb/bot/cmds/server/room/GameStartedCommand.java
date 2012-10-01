package com.fb.bot.cmds.server.room;

import com.fb.bot.Bot;
import com.fb.bot.cmds.Command;
import com.fb.bot.cmds.MessageCommand;
import com.fb.messages.server.room.GameStarted;

@MessageCommand(type = GameStarted.class)
public class GameStartedCommand extends Command<GameStarted> {

    public GameStartedCommand(Bot bot, GameStarted msg) {
	super(bot, msg);
    }

    @Override
    public Object execute() throws Exception {
	getBot().gameStarted(getMessage().getGameId(), getMessage().getMap());
	return null;
    }

}
