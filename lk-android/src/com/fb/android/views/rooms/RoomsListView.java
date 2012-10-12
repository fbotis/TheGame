package com.fb.android.views.rooms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fb.android.R;
import com.fb.android.views.PreGameView;
import com.fb.android.views.base.BaseActivity;
import com.fb.messages.client.general.GetSnapshot;
import com.fb.messages.client.room.CreateGame;
import com.fb.messages.server.general.Snapshot;
import com.fb.messages.server.general.Snapshot.Room;
import com.fb.messages.server.room.GameCreated;
import com.fb.messages.server.room.GameStarted;
import com.fb.messages.server.room.UserJoinedGame;
import com.fb.messages.server.room.UserUnjoinedGame;

public class RoomsListView extends BaseActivity {

    // XML node keys
    static final String KEY_GAME_NAME = "game_name";
    static final String KEY_GAME_CREATOR = "game_creator";
    static final String KEY_GAME_PLAYERS_COUNT = "game_players_no";
    static final String KEY_GAME_JOIN_BTN = "game_join_btn";

    ListView list;
    RoomsLazyAdapter adapter;
    private HashMap<String, Room> roomsById = new HashMap<String, Snapshot.Room>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.rooms);

	list = (ListView) findViewById(R.id.list);

	// Getting adapter by passing xml data ArrayList
	adapter = new RoomsLazyAdapter(this);
	list.setAdapter(adapter);

	// Click event for single list row
	list.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	    }
	});

	// TODO is ok here?
	sendClientMessage(new GetSnapshot(getClientId()));
    }

    @Override
    public void handleSnapshot(Snapshot msg) {
	for (Room r : msg.getRooms()) {
	    roomsById.put(r.getRoomId(), r);
	    adapter.addRoom(r);
	}
	adapter.notifyDataSetChanged();

    }

    @Override
    public void handleNewGame(GameCreated msg) {
	List<String> players = new ArrayList<String>();
	players.add(msg.getCreatorUserId());
	Room room = new Room(msg.getGameName(), msg.getGameId(), players, msg.getCreatorUserId());
	roomsById.put(msg.getGameId(), room);
	adapter.addRoom(room);
	adapter.notifyDataSetChanged();

	// the case when the game was created by this user
	if (room.getPlayers().contains(getClientId())) {
	    Intent intent = new Intent(this, PreGameView.class);
	    intent.putExtra(PreGameView.ROOM_EXTRA_ID, room);
	    startActivity(intent);
	}
    }

    @Override
    public void handleUserJoinedGame(UserJoinedGame msg) {
	// TODO handle the case when this arrives before snasphost or create
	// game
	Room room = getRoom(msg.getJoinedGameId());

	if (room != null) {
	    room.getPlayers().add(msg.getJoinedUserId());
	    adapter.updateRoom(room);
	    adapter.notifyDataSetChanged();
	}

	// if the joined user is the crt user
	if (getClientId().equals(msg.getJoinedUserId())) {
	    Intent intent = new Intent(this, PreGameView.class);
	    intent.putExtra(PreGameView.ROOM_EXTRA_ID, roomsById.get(msg.getJoinedGameId()));
	    startActivity(intent);
	}

    }

    private Room getRoom(String roomId) {
	return roomsById.get(roomId);
    }

    @Override
    public void handlerUserUnjoinedGame(UserUnjoinedGame msg) {
	// TODO handle the case when this arrives before snasphost or create
	// game
	Room room = getRoom(msg.getUnjoinedGameId());

	if (room != null) {
	    room.getPlayers().remove(msg.getUnjoinedUserId());
	    adapter.updateRoom(room);
	    adapter.notifyDataSetChanged();
	}

    }

    @Override
    public void handleGameStarted(GameStarted msg) {
	// TODO handle the case when this arrives before snasphost or create
	// game
	roomsById.remove(msg.getGameId());
	adapter.removeRoom(getRoom(msg.getGameId()));
	adapter.notifyDataSetChanged();
    }

}