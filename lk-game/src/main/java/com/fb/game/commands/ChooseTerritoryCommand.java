package com.fb.game.commands;

import com.fb.game.model.GameLogic;
import com.fb.messages.client.gameactions.ChooseTerritory;
import com.fb.messages.server.gameactions.BeginChallenge;
import com.fb.messages.server.gameactions.BeginChooseTerritory;
import com.fb.messages.server.gameactions.TerritoryChosen;
import com.fb.transport.IServerMessageSender;

@MessageCommand(type = ChooseTerritory.class)
public class ChooseTerritoryCommand extends Command<ChooseTerritory> {

    public ChooseTerritoryCommand(GameLogic gameLogic, IServerMessageSender serverSender, ChooseTerritory msg) {
	super(gameLogic, serverSender, msg);
    }

    @Override
    public void doWork() throws Exception {
	// TODO if all terrytories choosen start challenges
	TerritoryChosen terrChosen = getGameLogic().handleChooseTerritory(getMessage());
	sendServerMessage(terrChosen);
	// after territory was chosen and there are still not chosen terr send
	// begin terriori
	if (getGameLogic().isAnyTerritoryFree()) {
	    sendServerMessage(new BeginChooseTerritory(getMessage().getGameId(), getGameLogic().nextPlayer()));
	} else {
	    sendServerMessage(new BeginChallenge(getMessage().getGameId(), getGameLogic().nextPlayer()));
	}
    }

}
