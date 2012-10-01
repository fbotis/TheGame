package com.fb.bot;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fb.bot.robot.Bot;
import com.fb.messages.server.general.Snapshot;
import com.fb.messages.server.general.Snapshot.Room;

public class RoomsMonitor implements Runnable {

    private static final int MIN_PLAYERS_FOR_BOT_JOIN = 5;
    // TODO make it config
    private static Long JOIN_GAME_TIMEOUT = 3 * 1000l;
    private Map<String, Room> rooms = new ConcurrentHashMap<String, Snapshot.Room>();
    private Map<String, Long> roomsLastUpdate = new ConcurrentHashMap<String, Long>();
    private InProgressGameMonitor inprgMonitor;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private Bot bot;

    public RoomsMonitor(Bot bot) {
	inprgMonitor = new InProgressGameMonitor(bot);
	this.bot = bot;
	scheduler.scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);
    }

    public synchronized void endGame(String roomId) {
	rooms.remove(roomId);
	roomsLastUpdate.remove(roomId);
    }

    public synchronized void userJoinedRoom(String roomId, String playerId) {
	rooms.get(roomId).getPlayers().add(playerId);
	roomsLastUpdate.put(roomId, System.currentTimeMillis());
    }

    public synchronized void userUnjoinedGame(String roomId, String playerId) {
	rooms.get(roomId).getPlayers().remove(playerId);
	roomsLastUpdate.put(roomId, System.currentTimeMillis());
    }

    public synchronized void addRoom(Room room) {
	rooms.put(room.getRoomId(), room);
	roomsLastUpdate.put(room.getRoomId(), System.currentTimeMillis());
    }

    public synchronized void gameStarted(String roomId) {
	Room room = rooms.remove(roomId);
	roomsLastUpdate.remove(roomId);
	if (!bot.isJoinSent())
	    inprgMonitor.gameStarted(room);
    }

    @Override
    public synchronized void run() {
	if (!bot.isJoinSent()) {
	    for (Entry<String, Long> lastUpdateEntry : roomsLastUpdate.entrySet()) {
		if (System.currentTimeMillis() - lastUpdateEntry.getValue() > JOIN_GAME_TIMEOUT) {
		    if (rooms.get(lastUpdateEntry.getKey()).getPlayers().size() <= MIN_PLAYERS_FOR_BOT_JOIN) {
			bot.joinRoom(rooms.get(lastUpdateEntry.getKey()));
		    }
		}
	    }
	}
    }
}
