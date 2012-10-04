package com.fb.android.views;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.fb.android.R;
import com.fb.android.game.AndroidGameLogic;
import com.fb.android.views.base.BaseActivity;
import com.fb.messages.client.gameactions.Answer;
import com.fb.messages.client.gameactions.ChallengePlayer;
import com.fb.messages.client.gameactions.ChooseTerritory;
import com.fb.messages.server.gameactions.BeginChallenge;
import com.fb.messages.server.gameactions.BeginChooseTerritory;
import com.fb.messages.server.gameactions.ChallengeResult;
import com.fb.messages.server.gameactions.PlayerChallenged;
import com.fb.messages.server.gameactions.Question;
import com.fb.messages.server.gameactions.TerritoryChosen;
import com.fb.messages.server.general.Snapshot.Room;
import com.fb.messages.server.room.GameStarted;

public class GameView extends BaseActivity {

    public static final String ROOM_EXTRA_ID = "room";

    private final Map<Integer, Button> btns = new HashMap<Integer, Button>();

    private Room room;

    private boolean challenged;

    private AndroidGameLogic gameLogic;

    @Override
    public synchronized void onCreate(Bundle savedInstanceState) {
	setContentView(R.layout.game);
	this.room = (Room) getIntent().getSerializableExtra(ROOM_EXTRA_ID);

	btns.put(0, (Button) findViewById(R.id.game_map_btn0));
	btns.put(1, (Button) findViewById(R.id.game_map_btn1));
	btns.put(2, (Button) findViewById(R.id.game_map_btn2));
	btns.put(3, (Button) findViewById(R.id.game_map_btn3));
	btns.put(4, (Button) findViewById(R.id.game_map_btn4));
	btns.put(5, (Button) findViewById(R.id.game_map_btn5));
	btns.put(6, (Button) findViewById(R.id.game_map_btn6));
	btns.put(7, (Button) findViewById(R.id.game_map_btn7));
	btns.put(8, (Button) findViewById(R.id.game_map_btn8));
	disableButtons();
	super.onCreate(savedInstanceState);

    }

    @Override
    public synchronized void handleBeginChooseTerritory(BeginChooseTerritory msg) {
	if (msg.getNextPlayerId().equals(getClientId())) {
	    int index = 0;
	    for (Button btn : btns.values()) {
		final String terrId = String.valueOf(index);
		if (!gameLogic.getOwner(terrId).equals(getClientId())) {
		    btn.setEnabled(true);
		    btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			    sendClientMessage(new ChooseTerritory(getClientId(), room.getRoomId(), terrId));
			}
		    });
		}
		index++;
	    }
	}
    }

    @Override
    public synchronized void handleBeginChallenge(BeginChallenge msg) {
	if (msg.getNextPlayerId().equals(getClientId())) {
	    int index = 0;
	    for (Button btn : btns.values()) {
		final String terrId = String.valueOf(index);
		if (!gameLogic.getOwner(terrId).equals(getClientId())) {
		    btn.setEnabled(true);
		    btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			    sendClientMessage(new ChallengePlayer(getClientId(), room.getRoomId(), gameLogic
				    .getOwner(terrId), terrId));
			    challenged = true;
			}
		    });
		}
		index++;
	    }
	}
    }

    @Override
    public synchronized void handleChallengeResult(ChallengeResult msg) {
	gameLogic.assignTerritory(msg.getTerritoryId(), msg.getWinnerPlayerId());
	btns.get(Integer.valueOf(msg.getTerritoryId())).setText(msg.getWinnerPlayerId().subSequence(1, 4));
    }

    @Override
    public synchronized void handleEndGame(BeginChallenge msg) {
	// TODO Auto-generated method stub
	super.handleEndGame(msg);
    }

    @Override
    public synchronized void handlePlayerChallenged(PlayerChallenged msg) {
	if (msg.getChallengedPlayerId().equals(getClientId())) {
	    this.challenged = true;
	}
    }

    @Override
    public synchronized void handleQuestion(Question msg) {
	disableButtons();
	// TODO Auto-generated method stub
	if (isChallenged()) {
	    sendClientMessage(new Answer(getClientId(), room.getRoomId(), ""));
	}
	challenged = false;
    }

    private void disableButtons() {
	for (Button btn : btns.values()) {
	    btn.setEnabled(false);
	}
    }

    private boolean isChallenged() {
	return challenged;
    }

    @Override
    public synchronized void handleTerritoryChosen(TerritoryChosen msg) {
	disableButtons();
	gameLogic.assignTerritory(msg.getTerritoryId(), msg.getTerritoryUserId());
	btns.get(Integer.valueOf(msg.getTerritoryId())).setText(msg.getTerritoryUserId().subSequence(1, 4));
    }

    @Override
    public synchronized void handleGameStarted(GameStarted msg) {
	String[] map = new String[msg.getMap().size()];
	msg.getMap().toArray(map);
	gameLogic = new AndroidGameLogic(room, map);
    }

}
