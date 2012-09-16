package com.fb.game;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.fb.exceptions.game.InvalidGameException;
import com.fb.game.commands.Command;
import com.fb.game.commands.CommandFactory;
import com.fb.game.model.GameLogic;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.ServerBaseMessage;
import com.fb.messages.client.gameactions.GameActionBaseMessage;
import com.fb.messages.intercomponents.NewGame;
import com.fb.messages.server.ErrorMessage;
import com.fb.messages.server.gameactions.BeginChooseTerritory;
import com.fb.transport.IMessageHandler;
import com.fb.transport.IServerMessageSender;

public class GamesService implements IMessageHandler {

    private GamesManager gamesManager;
    private CommandFactory cmdFactory;

    // TODO make configurable
    private Executor threadPool = Executors.newFixedThreadPool(5);
    private IServerMessageSender srvSender;

    public GamesService(IServerMessageSender srvSender) {
	this.srvSender = srvSender;
	cmdFactory = new CommandFactory(gamesManager, srvSender);
	gamesManager = new GamesManager();
    }

    @Override
    public void handleClientMessage(ClientBaseMessage message) {
	if (message instanceof GameActionBaseMessage) {
	    String gameId = ((GameActionBaseMessage) message).getGameId();
	    if (gamesManager.getGameLogic(gameId) == null) {
		srvSender.sendServerMessage(new ErrorMessage(new InvalidGameException(message)));
		return;
	    }
	    Command cmd = cmdFactory.getCommand((GameActionBaseMessage) message);
	    threadPool.execute(cmd);
	} else {
	    // TODO client disconnected
	}
    }

    @Override
    public void handleServerMessage(ServerBaseMessage message) {
	if (message instanceof NewGame) {
	    GameLogic logic = gamesManager.createNewGame((NewGame) message);
	    srvSender.sendServerMessage(new BeginChooseTerritory(((NewGame) message).getRoomId(), logic.nextPlayer()));
	}
    }

}
