package com.fb.android;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fb.android.views.BaseActivity;
import com.fb.android.views.GameView;
import com.fb.messages.client.general.GetSnapshot;
import com.fb.messages.client.room.CreateGame;
import com.fb.messages.client.room.JoinGame;
import com.fb.messages.server.ErrorMessage;
import com.fb.messages.server.general.Snapshot;
import com.fb.messages.server.general.Snapshot.Room;
import com.fb.messages.server.room.GameCreated;
import com.fb.messages.server.room.GameStarted;
import com.fb.messages.server.room.UserJoinedGame;
import com.fb.messages.server.room.UserUnjoinedGame;
import com.fb.transport.IMessageHandler;

public class LKActivity extends BaseActivity implements IMessageHandler, OnItemLongClickListener {

    // LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<String>();

    // DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
    ArrayAdapter<String> adapter;

    // METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void createRoom(View v) {
	sendClientMessage(new CreateGame(getClientId(), "Game" + System.currentTimeMillis()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.rooms);
	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
	setListAdapter(adapter);
	getListView().setOnItemLongClickListener(this);
	sendClientMessage(new GetSnapshot(getClientId()));
    }

    public void handleSnapshot(Snapshot msg) {
	for (Room r : ((Snapshot) msg).getRooms()) {
	    listItems.add(r.getRoomId());
	}
	adapter.notifyDataSetChanged();
    }

    @Override
    protected void handleNewGame(GameCreated msg) {
	listItems.add(msg.getGameId());
	adapter.notifyDataSetChanged();
    }

    @Override
    protected void handleUserJoinedGame(UserJoinedGame msg) {
	if (msg.getJoinedUserId().equals(getClientId())) {
	    Toast.makeText(getApplicationContext(), "You joined game " + msg.getJoinedGameId(), Toast.LENGTH_LONG)
		    .show();
	    Intent intent = new Intent(this, GameView.class);
	    startActivity(intent);
	}
    }

    @Override
    protected void handleGameStarted(GameStarted msg) {
	// TODO Auto-generated method stub
	super.handleGameStarted(msg);
    }

    @Override
    protected void handlerErrorMessage(ErrorMessage msg) {
    }

    @Override
    protected void handlerUserUserUnjoinedGame(UserUnjoinedGame msg) {
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	sendClientMessage(new JoinGame(getClientId(), listItems.get(arg2)));
	return true;
    }
}