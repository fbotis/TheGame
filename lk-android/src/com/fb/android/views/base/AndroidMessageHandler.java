package com.fb.android.views.base;

import com.fb.messages.ClientBaseMessage;
import com.fb.messages.ServerBaseMessage;
import com.fb.messages.server.ErrorMessage;
import com.fb.messages.server.gameactions.BeginChallenge;
import com.fb.messages.server.gameactions.BeginChooseTerritory;
import com.fb.messages.server.gameactions.ChallengeResult;
import com.fb.messages.server.gameactions.EndGame;
import com.fb.messages.server.gameactions.PlayerChallenged;
import com.fb.messages.server.gameactions.Question;
import com.fb.messages.server.gameactions.TerritoryChosen;
import com.fb.messages.server.general.Snapshot;
import com.fb.messages.server.room.GameCreated;
import com.fb.messages.server.room.GameStarted;
import com.fb.messages.server.room.UserJoinedGame;
import com.fb.messages.server.room.UserUnjoinedGame;
import com.fb.transport.IMessageHandler;

import android.app.Activity;

public class AndroidMessageHandler implements IMessageHandler {
    private Activity activity;
    private ClientMessagesHandler clientMsgHandler;

    public AndroidMessageHandler(Activity activity, ClientMessagesHandler clientMessagesHandler) {
	this.activity = activity;
	this.clientMsgHandler = clientMessagesHandler;
    }

    @Override
    public void handleClientMessage(ClientBaseMessage arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void handleServerMessage(final ServerBaseMessage msg) {
	activity.runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		if (msg instanceof Snapshot) {
		    clientMsgHandler.handleSnapshot((Snapshot) msg);
		} else if (msg instanceof GameCreated) {
		    clientMsgHandler.handleNewGame((GameCreated) msg);
		} else if (msg instanceof GameStarted) {
		    clientMsgHandler.handleGameStarted((GameStarted) msg);
		} else if (msg instanceof UserJoinedGame) {
		    clientMsgHandler.handleUserJoinedGame((UserJoinedGame) msg);
		} else if (msg instanceof UserUnjoinedGame) {
		    clientMsgHandler.handlerUserUnjoinedGame((UserUnjoinedGame) msg);
		} else if (msg instanceof ErrorMessage) {
		    clientMsgHandler.handlerErrorMessage((ErrorMessage) msg);
		} else if (msg instanceof BeginChallenge) {
		    clientMsgHandler.handleBeginChallenge((BeginChallenge) msg);
		} else if (msg instanceof BeginChooseTerritory) {
		    clientMsgHandler.handleBeginChooseTerritory((BeginChooseTerritory) msg);
		} else if (msg instanceof ChallengeResult) {
		    clientMsgHandler.handleChallengeResult((ChallengeResult) msg);
		} else if (msg instanceof EndGame) {
		    clientMsgHandler.handleEndGame((BeginChallenge) msg);
		} else if (msg instanceof PlayerChallenged) {
		    clientMsgHandler.handlePlayerChallenged((PlayerChallenged) msg);
		} else if (msg instanceof Question) {
		    clientMsgHandler.handleQuestion((Question) msg);
		} else if (msg instanceof TerritoryChosen) {
		    clientMsgHandler.handleTerritoryChosen((TerritoryChosen) msg);
		}
	    }

	});
    }
}
