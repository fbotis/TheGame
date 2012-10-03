package com.fb.bot.cmds.server.game;

import com.fb.bot.Bot;
import com.fb.bot.cmds.Command;
import com.fb.bot.cmds.MessageCommand;
import com.fb.messages.server.gameactions.BeginChooseTerritory;

@MessageCommand(type = BeginChooseTerritory.class)
public class BeginChooseTerrCommand extends Command<BeginChooseTerritory> {

    public BeginChooseTerrCommand(Bot bot, BeginChooseTerritory msg) {
	super(bot, msg);
    }

    @Override
    public Object execute() throws Exception {
	if (getMessage().getNextPlayerId().equals(getBot().getClientId())) {
	    getBot().chooseTerritory();
	}
	return null;
    }

}
