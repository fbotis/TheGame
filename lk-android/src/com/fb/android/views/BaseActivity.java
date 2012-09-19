package com.fb.android.views;

import android.app.ListActivity;
import android.os.Bundle;

import com.fb.android.LKApplication;
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

public class BaseActivity extends ListActivity implements IMessageHandler {

    // TODO modify this
    private static volatile String clientID = "mobile" + System.nanoTime();;

    @Override
    protected void onResume() {
	((LKApplication) getApplication()).addMessageHandler(this);
	super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	((LKApplication) getApplication()).addMessageHandler(this);
	super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
	((LKApplication) getApplication()).removeMessageHandler(this);
	super.onPause();
    }

    protected void sendClientMessage(ClientBaseMessage msg) {
	((LKApplication) getApplication()).sendClientMsg(msg);
    }

    protected String getClientId() {
	return clientID;
    }

    @Override
    public void handleClientMessage(final ClientBaseMessage arg0) {
	// nothing to do
    }

    @Override
    public void handleServerMessage(final ServerBaseMessage msg) {
	this.runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		if (msg instanceof Snapshot) {
		    handleSnapshot((Snapshot) msg);
		} else if (msg instanceof GameCreated) {
		    handleNewGame((GameCreated) msg);
		} else if (msg instanceof GameStarted) {
		    handleGameStarted((GameStarted) msg);
		} else if (msg instanceof UserJoinedGame) {
		    handleUserJoinedGame((UserJoinedGame) msg);
		} else if (msg instanceof UserUnjoinedGame) {
		    handlerUserUserUnjoinedGame((UserUnjoinedGame) msg);
		} else if (msg instanceof ErrorMessage) {
		    handlerErrorMessage((ErrorMessage) msg);
		} else if (msg instanceof BeginChallenge) {
		    handleBeginChallenge((BeginChallenge) msg);
		} else if (msg instanceof BeginChooseTerritory) {
		    handleBeginChooseTerritory((BeginChooseTerritory) msg);
		} else if (msg instanceof ChallengeResult) {
		    handleChallengeResult((ChallengeResult) msg);
		} else if (msg instanceof EndGame) {
		    handleEndGame((BeginChallenge) msg);
		} else if (msg instanceof PlayerChallenged) {
		    handlePlayerChallenged((PlayerChallenged) msg);
		} else if (msg instanceof Question) {
		    handleQuestion((Question) msg);
		} else if (msg instanceof TerritoryChosen) {
		    handleTerritoryChosen((BeginChallenge) msg);
		}
	    }

	});
    }

    // *************** HANDLERS***************
    protected void handleTerritoryChosen(BeginChallenge msg) {
    }

    protected void handleQuestion(Question msg) {
    }

    protected void handlePlayerChallenged(PlayerChallenged msg) {
    }

    protected void handleEndGame(BeginChallenge msg) {
    }

    protected void handleChallengeResult(ChallengeResult msg) {
    }

    protected void handleBeginChooseTerritory(BeginChooseTerritory msg) {
    }

    protected void handleBeginChallenge(BeginChallenge msg) {
    }

    protected void handleUserJoinedGame(UserJoinedGame msg) {
    }

    protected void handlerErrorMessage(ErrorMessage msg) {
    }

    protected void handlerUserUserUnjoinedGame(UserUnjoinedGame msg) {
    }

    protected void handleGameStarted(GameStarted msg) {
    }

    protected void handleNewGame(GameCreated msg) {
    }

    protected void handleSnapshot(Snapshot msg) {
    }

}
