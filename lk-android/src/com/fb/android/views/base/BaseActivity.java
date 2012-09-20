package com.fb.android.views.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.fb.android.LKApplication;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.server.ErrorMessage;
import com.fb.messages.server.gameactions.BeginChallenge;
import com.fb.messages.server.gameactions.BeginChooseTerritory;
import com.fb.messages.server.gameactions.ChallengeResult;
import com.fb.messages.server.gameactions.PlayerChallenged;
import com.fb.messages.server.gameactions.Question;
import com.fb.messages.server.gameactions.TerritoryChosen;
import com.fb.messages.server.general.Snapshot;
import com.fb.messages.server.room.GameCreated;
import com.fb.messages.server.room.GameStarted;
import com.fb.messages.server.room.UserJoinedGame;
import com.fb.messages.server.room.UserUnjoinedGame;

public class BaseActivity extends Activity implements ClientMessagesHandler {

    private AndroidMessageHandler msgHandler;

    @Override
    public void onResume() {
	if (msgHandler == null) {
	    this.msgHandler = new AndroidMessageHandler(this, this);
	    ((LKApplication) getApplication()).addMessageHandler(msgHandler);
	}

	super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	if (msgHandler == null) {
	    this.msgHandler = new AndroidMessageHandler(this, this);
	    ((LKApplication) getApplication()).addMessageHandler(msgHandler);
	}
	super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
	if (msgHandler != null) {
	    ((LKApplication) getApplication()).removeMessageHandler(msgHandler);
	    msgHandler = null;
	}

	super.onPause();
    }

    public void sendClientMessage(ClientBaseMessage msg) {
	((LKApplication) getApplication()).sendClientMsg(msg);
    }

    public String getClientId() {
	return ((LKApplication) getApplication()).getClientId();
    }

    // *************** HANDLERS***************
    public void handleTerritoryChosen(TerritoryChosen msg) {
    }

    public void handleQuestion(Question msg) {
    }

    public void handlePlayerChallenged(PlayerChallenged msg) {
    }

    public void handleEndGame(BeginChallenge msg) {
    }

    public void handleChallengeResult(ChallengeResult msg) {
    }

    public void handleBeginChooseTerritory(BeginChooseTerritory msg) {
    }

    public void handleBeginChallenge(BeginChallenge msg) {
    }

    public void handleUserJoinedGame(UserJoinedGame msg) {
    }

    public void handlerErrorMessage(ErrorMessage msg) {
	Toast.makeText(getApplicationContext(), "ERROR:" + msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handlerUserUnjoinedGame(UserUnjoinedGame msg) {
    }

    public void handleGameStarted(GameStarted msg) {
    }

    public void handleNewGame(GameCreated msg) {
    }

    public void handleSnapshot(Snapshot msg) {
    }

}
