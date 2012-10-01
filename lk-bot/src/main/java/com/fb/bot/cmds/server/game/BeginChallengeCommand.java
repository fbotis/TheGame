package com.fb.bot.cmds.server.game;

import com.fb.bot.Bot;
import com.fb.bot.cmds.Command;
import com.fb.bot.cmds.MessageCommand;
import com.fb.messages.server.gameactions.BeginChallenge;

@MessageCommand(type = BeginChallenge.class)
public class BeginChallengeCommand extends Command<BeginChallenge> {

    public BeginChallengeCommand(Bot bot, BeginChallenge msg) {
	super(bot, msg);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Object execute() throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

}
