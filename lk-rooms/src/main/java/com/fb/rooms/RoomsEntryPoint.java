package com.fb.rooms;

import com.fb.messages.client.ClientDisconnected;
import com.fb.topics.Topic;
import com.fb.transport.MessagesTransport;

public class RoomsEntryPoint {

    private static final String[] DEFAULT_SUB_TOPICS = new String[] { Topic.ROOMS_ENGINE, Topic.CLIENT_DISCONNECTED };

    private MessagesTransport msgTransport;
    private RoomsService roomsService;

    public RoomsEntryPoint(String clientId, String brokerUrl) {
	msgTransport = new MessagesTransport(clientId, brokerUrl, Topic.CLIENT_DISCONNECTED, new ClientDisconnected(
		clientId), DEFAULT_SUB_TOPICS);
	roomsService = new RoomsService(msgTransport);
	msgTransport.setMsgHandler(roomsService);

    }
}
