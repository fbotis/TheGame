package com.fb.bot.cmds.server.game;

import com.fb.bot.Bot;
import com.fb.bot.cmds.Command;
import com.fb.bot.cmds.MessageCommand;
import com.fb.messages.server.gameactions.PlayerChallenged;

@MessageCommand(type = PlayerChallenged.class)
public class PlayerChallengedCommand extends Command<PlayerChallenged> {

    public PlayerChallengedCommand(Bot bot, PlayerChallenged msg) {
	super(bot, msg);
    }

    @Override
    public Object execute() throws Exception {
	if (getMessage().getChallengedPlayerId().equals(getBot().getClientId())) {
	    getBot().ownTerritoryChallenged(getMessage().getTerritoryId(), getMessage().getChallengedPlayerId());
	}
	return null;
    }
}
