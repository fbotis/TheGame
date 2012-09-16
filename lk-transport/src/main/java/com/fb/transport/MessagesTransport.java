package com.fb.transport;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.fb.messages.BaseMessage;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.MsgDeserializer;
import com.fb.messages.ServerBaseMessage;
import com.fb.messages.client.ClientDisconnected;

public class MessagesTransport implements IServerMessageSender, IClientMessageSender, ITransportListener {
    private static final Logger logger = Logger.getLogger(MessagesTransport.class);

    private MsgDeserializer deserializer = new MsgDeserializer();
    private MqttTransport mqttTransport;
    private IMessageHandler msgHandler = new IMessageHandler() {

	@Override
	public void handleServerMessage(ServerBaseMessage message) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void handleClientMessage(ClientBaseMessage message) {
	    // TODO Auto-generated method stub

	}
    };
    private AtomicLong queueCount = new AtomicLong();

    public MessagesTransport(String clientId, String brokerUrl, String clientDisconnectedTopic,
	    BaseMessage clientDisconnectedMessage, String[] subscribedTopics, IMessageHandler msgHandler) {
	try {
	    mqttTransport = new MqttTransport(clientId, brokerUrl, clientDisconnectedTopic, clientDisconnectedMessage,
		    this, subscribedTopics);
	    this.msgHandler = msgHandler;
	} catch (MqttException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public MessagesTransport(String clientId, String brokerUrl, String clientDisconnectedTopic,
	    BaseMessage clientDisconnectedMessage, String[] subscribedTopics) {
	try {
	    mqttTransport = new MqttTransport(clientId, brokerUrl, clientDisconnectedTopic, clientDisconnectedMessage,
		    this, subscribedTopics);
	} catch (MqttException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    public void sendClientMessage(ClientBaseMessage msg) {
	sendMessage(msg);
    }

    @Override
    public void sendServerMessage(ServerBaseMessage msg) {
	sendMessage(msg);
    }

    private void sendMessage(BaseMessage msg) {
	for (String topic : msg.getTopics())
	    try {
		mqttTransport.sendMessage(topic, msg.toString());
		queueCount.incrementAndGet();
	    } catch (Exception e) {
		// TODO ???
	    }
    }

    @Override
    public void connectionLost(Throwable arg0) {
	// TODO Auto-generated method stub
	arg0.printStackTrace();

    }

    @Override
    public void messageReceived(String topic, String message) {
	logger.debug("[RECEIVED] " + topic + "---" + message);
	try {
	    BaseMessage msg = deserializer.fromJsonMsg(message);
	    if (msg instanceof ClientBaseMessage) {
		msgHandler.handleClientMessage((ClientBaseMessage) msg);
	    } else {
		msgHandler.handleServerMessage((ServerBaseMessage) msg);
	    }
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    @Override
    public void messageSent() {
	queueCount.decrementAndGet();
	logger.debug("Message sent confirmation. queue count: " + queueCount);

    }

    public void setMsgHandler(IMessageHandler msgHandler) {
	this.msgHandler = msgHandler;
    }

    @Override
    public void subscribeToTopic(String topic) throws MqttSecurityException, MqttException {
	mqttTransport.subscribeToTopic(topic);

    }

    @Override
    public void unsubscribeFromTopic(String topic) throws MqttException {
	mqttTransport.unsuscribeFromTopic(topic);
    }
}
