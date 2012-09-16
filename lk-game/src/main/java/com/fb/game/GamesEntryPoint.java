package com.fb.game;

import com.fb.messages.client.ClientDisconnected;
import com.fb.topics.Topic;
import com.fb.transport.MessagesTransport;

public class GamesEntryPoint {
    private static final String[] DEFAULT_SUB_TOPICS = new String[] { Topic.INTER_COMPONENT_GAME_ENGINE,
	    Topic.CLIENT_DISCONNECTED };

    private MessagesTransport msgTransport;
    private GamesService gamesService;

    public GamesEntryPoint(String clientId, String brokerUrl) {
	msgTransport = new MessagesTransport(clientId, brokerUrl, Topic.CLIENT_DISCONNECTED, new ClientDisconnected(
		clientId), DEFAULT_SUB_TOPICS);
	gamesService = new GamesService(msgTransport);
	msgTransport.setMsgHandler(gamesService);
    }
}
