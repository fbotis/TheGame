package com.fb.bot.robot;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.fb.bot.RoomsMonitor;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.client.general.GetSnapshot;
import com.fb.messages.client.room.JoinGame;
import com.fb.messages.server.general.Snapshot.Room;
import com.fb.transport.MessagesTransport;

public class Bot {
    private String botId;
    private String botName;
    private MessagesTransport msgTransport;
    private RoomsMonitor roomsMonitor;
    private boolean joinSent;
    private boolean playing;
    private List<String> crtMap;
    private Room crtRoom;

    public Bot(String botId, String botName, MessagesTransport msgTransport) {
	this.botId = botId;
	this.botName = botName;
	this.msgTransport = msgTransport;
	this.roomsMonitor = new RoomsMonitor(this);
    }

    public void sendMessage(ClientBaseMessage msg) {
	msgTransport.sendClientMessage(msg);
    }

    public void addRoom(Room room) {
	roomsMonitor.addRoom(room);
    }

    public void joinRoom(Room room) {
	this.joinSent = true;
	this.crtRoom = room;
	try {
	    msgTransport.subscribeToTopic(room.getRoomId());
	} catch (MqttSecurityException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (MqttException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	sendMessage(new JoinGame(getClientId(), room.getRoomId()));
    }

    public boolean isPlaying() {
	return playing;
    }

    public boolean isJoinSent() {
	return joinSent;
    }

    public String getClientId() {
	return botId;
    }

    public void gameStarted(String gameId, List<String> map) {
	if (isPlaying()) {
	    this.crtMap = map;
	}
    }

    public void userJoinedRoom(String joinedGameId, String joinedUserId) {
	if (isJoinSent()) {
	    playing = true;
	}
	roomsMonitor.userJoinedRoom(joinedGameId, joinedUserId);
    }

    public void userUnjoinedRoom(String unjoinedGameId, String unjoinedUserId) {
	roomsMonitor.userUnjoinedGame(unjoinedGameId, unjoinedUserId);
    }

    public void endGame(String roomId) {
	if (roomId.equals(crtRoom.getRoomId())) {
	    joinSent = false;
	    playing = false;
	    crtRoom = null;
	    try {
		msgTransport.unsubscribeFromTopic(roomId);
	    } catch (MqttException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	roomsMonitor.endGame(roomId);

    }

    public void addRooms(List<Room> rooms) {
	for (Room room : rooms) {
	    roomsMonitor.addRoom(room);
	}
    }
}
