package com.fb.rooms.commands;

import java.util.ArrayList;
import java.util.List;

import com.fb.messages.client.general.GetSnapshot;
import com.fb.messages.server.general.Snapshot;
import com.fb.rooms.RoomsManager;
import com.fb.rooms.model.Room;
import com.fb.transport.IServerMessageSender;

@MessageCommand(type = GetSnapshot.class)
public class GetSnapshotCommand extends Command<GetSnapshot> {

    public GetSnapshotCommand(RoomsManager roomsManager, IServerMessageSender serverSender, GetSnapshot msg) {
	super(roomsManager, serverSender, msg);
    }

    @Override
    public void doWork() throws Exception {
	List<com.fb.messages.server.general.Snapshot.Room> roomPlayers = new ArrayList<com.fb.messages.server.general.Snapshot.Room>();
	for (Room room : getRoomsManager().getAllRoomsByRoomId().values()) {
	    roomPlayers.add(new com.fb.messages.server.general.Snapshot.Room(room.getName(), room.getId(), room
		    .getUsers(), room.getCreatorId()));
	}
	sendServerMessage(new Snapshot(getMessage().getUserId(), roomPlayers));

    }
}
