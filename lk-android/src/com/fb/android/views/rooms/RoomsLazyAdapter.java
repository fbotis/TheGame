package com.fb.android.views.rooms;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fb.android.R;
import com.fb.android.views.base.BaseActivity;
import com.fb.messages.client.room.JoinGame;
import com.fb.messages.server.general.Snapshot.Room;

public class RoomsLazyAdapter extends BaseAdapter {

    private BaseActivity activity;
    private HashMap<String, Room> dataMap = new HashMap<String, Room>();
    private ArrayList<Room> dataList = new ArrayList<Room>();
    private static LayoutInflater inflater = null;

    public RoomsLazyAdapter(BaseActivity a) {
	activity = a;
	inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
	return dataList.size();
    }

    public Object getItem(int position) {
	return position;
    }

    public long getItemId(int position) {
	return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
	View vi = convertView;
	if (convertView == null)
	    vi = inflater.inflate(R.layout.room_item, null);

	TextView gameName = (TextView) vi.findViewById(R.id.game_name);
	TextView gameCreator = (TextView) vi.findViewById(R.id.game_creator);
	TextView gamePlayers = (TextView) vi.findViewById(R.id.game_players_no);
	ImageView joinBtn = (ImageView) vi.findViewById(R.id.game_join_btn);

	final Room room = dataList.get(position);

	// Setting all values in listview
	gameName.setText(room.getName());
	gameCreator.setText(room.getCreatorId());
	gamePlayers.setText("Players: " + room.getPlayers().size());
	joinBtn.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View arg0) {
		activity.sendClientMessage(new JoinGame(activity.getClientId(), room.getRoomId()));
	    }
	});

	return vi;
    }

    public void addRoom(Room r) {
	dataList.add(r);
	dataMap.put(r.getRoomId(), r);
    }

    public void updateRoom(Room room) {
	for (Room dataRoom : dataList) {
	    if (room.equals(dataRoom)) {
		dataRoom = room;
	    }
	}
	dataMap.put(room.getRoomId(), room);
    }

    public void removeRoom(Room room) {
	dataList.remove(room);
	dataMap.remove(room.getRoomId());
    }

}