package com.fb.android.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fb.android.LKApplication;
import com.fb.android.R;
import com.fb.android.views.base.BaseActivity;
import com.fb.messages.client.room.StartGame;
import com.fb.messages.server.general.Snapshot.Room;
import com.fb.messages.server.room.GameStarted;
import com.fb.messages.server.room.UserJoinedGame;
import com.fb.messages.server.room.UserUnjoinedGame;

public class PreGameView extends BaseActivity {

    public static final String ROOM_EXTRA_ID = "room";
    private Room room;

    private TextView playersText;
    private Button startGameBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.pre_game);
	this.room = (Room) getIntent().getSerializableExtra(ROOM_EXTRA_ID);

	playersText = (TextView) findViewById(R.id.pre_game_playersText);
	setPlayersText();

	startGameBtn = (Button) findViewById(R.id.pre_game_start_game_btn);
	startGameBtn.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		sendClientMessage(new StartGame(getClientId(), room.getRoomId()));
		((LKApplication) getApplication()).subscribeToTopic("S" + room.getRoomId());
		((LKApplication) getApplication()).removeMessageHandler(msgHandler);
		Toast.makeText(getApplicationContext(), "The game was started!!!", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(getApplicationContext(), GameView.class);
		intent.putExtra(GameView.ROOM_EXTRA_ID, room);
		startActivity(intent);
	    }
	});
    }

    private void setPlayersText() {
	String txt = "";
	for (String player : room.getPlayers()) {
	    txt += player + "\n";
	}
	playersText.setText(txt);
    }

    @Override
    public void handleUserJoinedGame(UserJoinedGame msg) {
	if (msg.getJoinedGameId().equals(room.getRoomId())) {
	    room.getPlayers().add(msg.getJoinedUserId());
	    setPlayersText();
	}
    }

    @Override
    public void handlerUserUnjoinedGame(UserUnjoinedGame msg) {
	if (msg.getUnjoinedGameId().equals(room.getRoomId())) {
	    room.getPlayers().remove(msg.getUnjoinedUserId());
	    setPlayersText();
	}
    }

}
