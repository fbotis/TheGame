package com.fb.bot.cmds.server.game;

import com.fb.bot.Bot;
import com.fb.bot.cmds.Command;
import com.fb.bot.cmds.MessageCommand;
import com.fb.messages.server.gameactions.BeginChooseTerritory;

@MessageCommand(type=BeginChooseTerritory.class)
public class BeginChooseTerrCommand extends Command<BeginChooseTerritory>{

    public BeginChooseTerrCommand(Bot bot, BeginChooseTerritory msg) {
	super(bot, msg);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Object execute() throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

}
