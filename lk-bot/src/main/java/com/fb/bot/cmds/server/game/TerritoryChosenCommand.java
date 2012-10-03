package com.fb.bot.cmds.server.game;

import com.fb.bot.Bot;
import com.fb.bot.cmds.Command;
import com.fb.bot.cmds.MessageCommand;
import com.fb.messages.server.gameactions.TerritoryChosen;

@MessageCommand(type = TerritoryChosen.class)
public class TerritoryChosenCommand extends Command<TerritoryChosen> {

    public TerritoryChosenCommand(Bot bot, TerritoryChosen msg) {
	super(bot, msg);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Object execute() throws Exception {
	getBot().assignTerritory(getMessage().getTerritoryId(), getMessage().getTerritoryUserId());
	return null;
    }

}
