package com.fb.bot.robot.cmds.server;

import com.fb.bot.robot.Bot;
import com.fb.bot.robot.cmds.Command;
import com.fb.bot.robot.cmds.MessageCommand;
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
