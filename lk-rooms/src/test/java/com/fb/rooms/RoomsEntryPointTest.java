package com.fb.rooms;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import com.fb.messages.ClientBaseMessage;
import com.fb.messages.ServerBaseMessage;
import com.fb.messages.client.ClientDisconnected;
import com.fb.messages.client.room.CreateGame;
import com.fb.messages.client.room.JoinGame;
import com.fb.messages.client.room.StartGame;
import com.fb.messages.client.room.UnjoinGame;
import com.fb.messages.server.room.GameCreated;
import com.fb.topics.Topic;
import com.fb.transport.IMessageHandler;
import com.fb.transport.MessagesTransport;

public class RoomsEntryPointTest {
    @Test
    public void testSmoke() throws Exception {
	RoomsEntryPoint entry = new RoomsEntryPoint("rooms" + System.nanoTime(), "tcp://localhost:1883");

	final String userId = "client" + System.nanoTime();
	final CountDownLatch latch = new CountDownLatch(1);
	final AtomicReference<String> gameId = new AtomicReference<String>();
	MessagesTransport client1 = new MessagesTransport(userId, "tcp://localhost:1883", Topic.CLIENT_DISCONNECTED,
		new ClientDisconnected(userId), new String[] { Topic.ALL_TOPIC, userId }, new IMessageHandler() {

		    @Override
		    public void handleServerMessage(ServerBaseMessage message) {
			System.out.println("[" + userId + "]" + "TO CLIENT SERVER MSG:" + message);
			if (message instanceof GameCreated) {
			    gameId.set(((GameCreated) message).getGameId());
			}

		    }

		    @Override
		    public void handleClientMessage(ClientBaseMessage message) {
			System.out.println("[" + userId + "]" + "TO CLIENT Client MSG:" + message);

		    }
		});

	final String userId2 = "client" + System.nanoTime();
	MessagesTransport client2 = new MessagesTransport(userId2, "tcp://localhost:1883", Topic.CLIENT_DISCONNECTED,
		new ClientDisconnected(userId), new String[] { Topic.ALL_TOPIC, userId2 }, new IMessageHandler() {

		    @Override
		    public void handleServerMessage(ServerBaseMessage message) {
			System.out.println("[" + userId2 + "]" + "TO CLIENT SERVER MSG:" + message);

		    }

		    @Override
		    public void handleClientMessage(ClientBaseMessage message) {
			System.out.println("[" + userId2 + "]" + "TO CLIENT Client MSG:" + message);

		    }
		});

	final String userId3 = "client" + System.nanoTime();
	MessagesTransport client3 = new MessagesTransport(userId3, "tcp://localhost:1883", Topic.CLIENT_DISCONNECTED,
		new ClientDisconnected(userId), new String[] { Topic.ALL_TOPIC, userId3 }, new IMessageHandler() {

		    @Override
		    public void handleServerMessage(ServerBaseMessage message) {
			System.out.println("[" + userId3 + "]" + "TO CLIENT SERVER MSG:" + message);

		    }

		    @Override
		    public void handleClientMessage(ClientBaseMessage message) {
			System.out.println("[" + userId3 + "]" + "TO CLIENT Client MSG:" + message);

		    }
		});

	client1.sendClientMessage(new CreateGame(userId, "new game"));

	client1.sendClientMessage(new JoinGame(userId, gameId.get()));
	client2.sendClientMessage(new JoinGame(userId2, gameId.get()));
	client3.sendClientMessage(new JoinGame(userId3, gameId.get()));
	client2.sendClientMessage(new CreateGame(userId2, "new game2"));
	client3.sendClientMessage(new JoinGame(userId3, gameId.get()));
	latch.await();
    }
}
