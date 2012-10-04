package com.fb.android.views.base;

import android.app.ExpandableListActivity;
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

public class ExpandleListBaseActivity extends ExpandableListActivity implements ClientMessagesHandler {
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
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handleQuestion(Question msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handlePlayerChallenged(PlayerChallenged msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handleEndGame(BeginChallenge msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handleChallengeResult(ChallengeResult msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handleBeginChooseTerritory(BeginChooseTerritory msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handleBeginChallenge(BeginChallenge msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handleUserJoinedGame(UserJoinedGame msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handlerErrorMessage(ErrorMessage msg) {
	Toast.makeText(getApplicationContext(), "ERROR:" + msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handlerUserUnjoinedGame(UserUnjoinedGame msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handleGameStarted(GameStarted msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handleNewGame(GameCreated msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void handleSnapshot(Snapshot msg) {
	Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_LONG).show();
    }
}
