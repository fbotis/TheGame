package com.fb.android.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.fb.android.R;
import com.fb.android.views.base.ExpandleListBaseActivity;
import com.fb.messages.client.general.GetSnapshot;
import com.fb.messages.client.room.JoinGame;
import com.fb.messages.server.general.Snapshot;
import com.fb.messages.server.general.Snapshot.Room;
import com.fb.messages.server.room.GameCreated;
import com.fb.messages.server.room.GameStarted;
import com.fb.messages.server.room.UserJoinedGame;
import com.fb.messages.server.room.UserUnjoinedGame;

public class RoomsListView extends ExpandleListBaseActivity {

    private RoomsExpandableListAdapter mAdapter;
    private HashMap<String, Room> roomsById = new HashMap<String, Snapshot.Room>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
	// Set up our adapter
	mAdapter = new RoomsExpandableListAdapter();
	setListAdapter(mAdapter);
	registerForContextMenu(getExpandableListView());

	super.onCreate(savedInstanceState);
	// TODO is ok here
	sendClientMessage(new GetSnapshot(getClientId()));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
	ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

	String title = ((TextView) info.targetView).getText().toString();

	int type = ExpandableListView.getPackedPositionType(info.packedPosition);
	if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
	    int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
	    int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
	    Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos, Toast.LENGTH_SHORT)
		    .show();
	    return true;
	} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
	    int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
	    Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
	    return true;
	}

	return false;
    }

    @Override
    public void handleSnapshot(Snapshot msg) {
	for (Room r : msg.getRooms()) {
	    roomsById.put(r.getRoomId(), r);
	    mAdapter.addRoom(r);
	}
	mAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleNewGame(GameCreated msg) {
	List<String> players = new ArrayList<String>();
	players.add(msg.getCreatorUserId());
	Room room = new Room(msg.getGameName(), msg.getGameId(), players, msg.getCreatorUserId());
	roomsById.put(msg.getGameId(), room);
	mAdapter.addRoom(room);
	mAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleUserJoinedGame(UserJoinedGame msg) {
	// TODO handle the case when this arrives before snasphost or create
	// game
	if (roomsById.get(msg.getJoinedGameId()) != null)
	    roomsById.get(msg.getJoinedGameId()).getPlayers().add(msg.getJoinedUserId());
	mAdapter.notifyDataSetChanged();
	if (getClientId().equals(msg.getJoinedUserId())) {
	    Intent intent = new Intent(this, PreGameView.class);
	    intent.putExtra(PreGameView.ROOM_EXTRA_ID, roomsById.get(msg.getJoinedGameId()));
	    startActivity(intent);
	}
    }

    @Override
    public void handlerUserUnjoinedGame(UserUnjoinedGame msg) {
	// TODO handle the case when this arrives before snasphost or create
	// game
	roomsById.get(msg.getUnjoinedGameId()).getPlayers().remove(msg.getUnjoinedUserId());
	mAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleGameStarted(GameStarted msg) {
	// TODO handle the case when this arrives before snasphost or create
	// game
	roomsById.remove(msg.getGameId());
	mAdapter.notifyDataSetChanged();
    }

    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids. Each
     * photo is displayed as an image. This adapter supports clearing the list
     * of photos and adding a new photo.
     * 
     */
    public class RoomsExpandableListAdapter extends BaseExpandableListAdapter {
	class RoomViewHolder {
	    TextView roomName;
	}

	class RoomDetailsViewHolder {
	    TextView playersNo;
	    Button joinBtn;
	}

	private HashMap<Integer, Room> rooms = new HashMap<Integer, Room>();

	public Room getChild(int groupPosition, int childPosition) {
	    return rooms.get(groupPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
	    return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
	    return 1;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
		ViewGroup parent) {
	    RoomDetailsViewHolder holder;
	    final Room item = getChild(groupPosition, childPosition);
	    if (convertView == null) {
		convertView = View.inflate(getApplicationContext(), R.layout.room_details, null);
		holder = new RoomDetailsViewHolder();
		holder.playersNo = (TextView) convertView.findViewById(R.id.room_detail_players_no);
		holder.joinBtn = (Button) convertView.findViewById(R.id.room_detail_join_btn);
		convertView.setTag(holder);
	    } else {
		holder = (RoomDetailsViewHolder) convertView.getTag();
	    }

	    if (item != null) {
		holder.playersNo.setText("Players no: " + item.getPlayers().size());
		holder.joinBtn.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			sendClientMessage(new JoinGame(getClientId(), item.getRoomId()));
		    }
		});
	    }
	    return convertView;
	}

	public Room getGroup(int groupPosition) {
	    return rooms.get(groupPosition);
	}

	public int getGroupCount() {
	    return rooms.size();
	}

	public long getGroupId(int groupPosition) {
	    return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	    RoomViewHolder holder;
	    Room item = getGroup(groupPosition);
	    if (convertView == null) {
		convertView = View.inflate(getApplicationContext(), R.layout.room_group, null);
		holder = new RoomViewHolder();
		holder.roomName = (TextView) convertView.findViewById(R.id.room_group_name);
		convertView.setTag(holder);
	    } else {
		holder = (RoomViewHolder) convertView.getTag();
	    }

	    if (item != null) {
		holder.roomName.setText(item.getName());
	    }
	    return convertView;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
	    return true;
	}

	public boolean hasStableIds() {
	    return true;
	}

	public void addRoom(Room room) {
	    rooms.put(rooms.size(), room);
	}

    }
}