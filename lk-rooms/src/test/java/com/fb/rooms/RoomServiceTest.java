package com.fb.rooms;

import java.util.concurrent.CountDownLatch;

import junit.framework.TestCase;

import org.junit.Test;

import com.fb.messages.ServerBaseMessage;
import com.fb.messages.client.room.CreateGame;
import com.fb.messages.client.room.JoinGame;
import com.fb.messages.client.room.StartGame;
import com.fb.messages.client.room.UnjoinGame;
import com.fb.messages.server.ErrorMessage;
import com.fb.messages.server.room.GameCreated;
import com.fb.messages.server.room.GameStarted;
import com.fb.messages.server.room.UserJoinedGame;
import com.fb.messages.server.room.UserUnjoinedGame;
import com.fb.transport.IServerMessageSender;

/**
 * Unit test for simple App.
 */
public class RoomServiceTest extends TestCase {
    private CountDownLatch latch = new CountDownLatch(1);
    private ServerBaseMessage lastSent;

    private final IServerMessageSender serverSender = new IServerMessageSender() {

	@Override
	public void sendServerMessage(ServerBaseMessage arg0) {
	    lastSent = arg0;
	    latch.countDown();
	}

	@Override
	public void subscribeToTopic(String topic) throws Exception {
	    // TODO Auto-generated method stub

	}

	@Override
	public void unsubscribeFromTopic(String topic) throws Exception {
	    // TODO Auto-generated method stub

	}

	@Override
	public boolean isConnected() {
	    // TODO Auto-generated method stub
	    return false;
	}
    };

    @Test
    public void testSmokeServ() throws Exception {

	RoomsService roomsService = new RoomsService(serverSender);

	CreateGame createGame = new CreateGame("1", "g1");
	roomsService.handleClientMessage(createGame);
	waitServerResponse();

	assertEquals(lastSent.getClass(), GameCreated.class);
	String roomId = ((GameCreated) lastSent).getGameId();

	JoinGame joinGame = new JoinGame("2", roomId);
	roomsService.handleClientMessage(joinGame);
	waitServerResponse();
	assertEquals(lastSent.getClass(), UserJoinedGame.class);
	String joinedUser = ((UserJoinedGame) lastSent).getJoinedUserId();
	String joinedGameId = ((UserJoinedGame) lastSent).getJoinedGameId();
	assertEquals("2", joinedUser);
	assertEquals(roomId, joinedGameId);

	JoinGame joinGame2 = new JoinGame("1", roomId);
	roomsService.handleClientMessage(joinGame2);
	waitServerResponse();
	assertEquals(lastSent.getClass(), ErrorMessage.class);

	UnjoinGame unjoinGame = new UnjoinGame("1", roomId);
	roomsService.handleClientMessage(unjoinGame);
	waitServerResponse();
	assertEquals(lastSent.getClass(), UserUnjoinedGame.class);

	StartGame startGame = new StartGame("2", roomId);
	roomsService.handleClientMessage(startGame);
	waitServerResponse();
	assertEquals(lastSent.getClass(), GameStarted.class);
    }

    private void waitServerResponse() {
	try {
	    latch.await();
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	latch = new CountDownLatch(1);
    }
}
