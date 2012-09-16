package com.fb.game.commands;

import com.fb.game.model.GameLogic;
import com.fb.messages.client.gameactions.ChooseTerritory;
import com.fb.messages.server.gameactions.TerritoryChosen;
import com.fb.transport.IServerMessageSender;

@MessageCommand(type = ChooseTerritory.class)
public class ChooseTerritoryCommand extends Command<ChooseTerritory> {

    public ChooseTerritoryCommand(GameLogic gameLogic, IServerMessageSender serverSender, ChooseTerritory msg) {
	super(gameLogic, serverSender, msg);
    }

    @Override
    public void doWork() throws Exception {
	TerritoryChosen terrChosen = getGameLogic().handleChooseTerritory(getMessage());
	sendServerMessage(terrChosen);
    }

}
