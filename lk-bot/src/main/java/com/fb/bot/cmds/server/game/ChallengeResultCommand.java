package com.fb.bot.cmds.server.game;

import com.fb.bot.Bot;
import com.fb.bot.cmds.Command;
import com.fb.bot.cmds.MessageCommand;
import com.fb.messages.server.gameactions.ChallengeResult;

@MessageCommand(type = ChallengeResult.class)
public class ChallengeResultCommand extends Command<ChallengeResult> {

    public ChallengeResultCommand(Bot bot, ChallengeResult msg) {
	super(bot, msg);
    }

    @Override
    public Object execute() throws Exception {
	getBot().assignTerritory(getMessage().getTerritoryId(), getMessage().getWinnerPlayerId());
	return null;
    }

}
