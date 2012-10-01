package com.fb.bot.robot.cmds.server;

import com.fb.bot.robot.Bot;
import com.fb.bot.robot.cmds.Command;
import com.fb.bot.robot.cmds.MessageCommand;
import com.fb.messages.server.gameactions.EndGame;

@MessageCommand(type = EndGame.class)
public class EndGameCommand extends Command<EndGame> {

    public EndGameCommand(Bot bot, EndGame msg) {
	super(bot, msg);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Object execute() throws Exception {
	getBot().endGame(getMessage().getGameId());
	return null;
    }
}
