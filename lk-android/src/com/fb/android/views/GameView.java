package com.fb.android.views;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.fb.android.R;
import com.fb.android.views.base.BaseActivity;
import com.fb.messages.client.gameactions.ChooseTerritory;
import com.fb.messages.server.gameactions.BeginChallenge;
import com.fb.messages.server.gameactions.BeginChooseTerritory;
import com.fb.messages.server.gameactions.ChallengeResult;
import com.fb.messages.server.gameactions.PlayerChallenged;
import com.fb.messages.server.gameactions.Question;
import com.fb.messages.server.gameactions.TerritoryChosen;
import com.fb.messages.server.general.Snapshot.Room;

public class GameView extends BaseActivity {

    public static final String ROOM_EXTRA_ID = "room";

    private Map<Integer, Button> btns = new HashMap<Integer, Button>();

    private Room room;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
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

	for (final Entry<Integer, Button> btn : btns.entrySet()) {
	    btn.getValue().addTextChangedListener(new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    // TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable txt) {
		    if (!txt.toString().equals("Empty")) {
			btn.getValue().setEnabled(false);
		    }
		}
	    });

	    btn.getValue().setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    sendClientMessage(new ChooseTerritory(getClientId(), room.getRoomId(), btn.getKey().toString()));
		}
	    });
	}
    }

    @Override
    public void handleBeginChooseTerritory(BeginChooseTerritory msg) {
	// TODO Auto-generated method stub
	super.handleBeginChooseTerritory(msg);
    }

    @Override
    public void handleBeginChallenge(BeginChallenge msg) {
	// TODO Auto-generated method stub
	super.handleBeginChallenge(msg);
    }

    @Override
    public void handleChallengeResult(ChallengeResult msg) {
	// TODO Auto-generated method stub
	super.handleChallengeResult(msg);
    }

    @Override
    public void handleEndGame(BeginChallenge msg) {
	// TODO Auto-generated method stub
	super.handleEndGame(msg);
    }

    @Override
    public void handlePlayerChallenged(PlayerChallenged msg) {
	// TODO Auto-generated method stub
	super.handlePlayerChallenged(msg);
    }

    @Override
    public void handleQuestion(Question msg) {
	// TODO Auto-generated method stub
	super.handleQuestion(msg);
    }

    @Override
    public void handleTerritoryChosen(TerritoryChosen msg) {
	btns.get(Integer.valueOf(msg.getTerritoryId())).setText(msg.getTerritoryUserId().subSequence(1, 3));
    }

}
