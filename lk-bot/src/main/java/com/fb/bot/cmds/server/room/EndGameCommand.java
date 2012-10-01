package com.fb.bot.cmds.server.room;

import com.fb.bot.Bot;
import com.fb.bot.cmds.Command;
import com.fb.bot.cmds.MessageCommand;
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
