package com.fb.transport;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.fb.messages.BaseMessage;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.MsgDeserializer;
import com.fb.messages.ServerBaseMessage;

/**
 * Common class used to comunicate with mqtt
 * 
 * @author Flo
 * 
 */
public class MessagesTransport implements IServerMessageSender, IClientMessageSender, ITransportListener {
    private static final Logger logger = Logger.getLogger(MessagesTransport.class);

    private MsgDeserializer deserializer = new MsgDeserializer();
    private MqttTransport mqttTransport;
    private IMessageHandler msgHandler = new IMessageHandler() {

	@Override
	public void handleServerMessage(ServerBaseMessage message) {
	}

	@Override
	public void handleClientMessage(ClientBaseMessage message) {

	}
    };
    private AtomicLong queueCount = new AtomicLong();

    public MessagesTransport(String clientId, String brokerUrl, String clientDisconnectedTopic,
	    BaseMessage clientDisconnectedMessage, String[] subscribedTopics, IMessageHandler msgHandler)
	    throws MqttException {
	mqttTransport = new MqttTransport(clientId, brokerUrl, clientDisconnectedTopic, clientDisconnectedMessage,
		this, subscribedTopics);
	this.msgHandler = msgHandler;
    }

    public MessagesTransport(String clientId, String brokerUrl, String clientDisconnectedTopic,
	    BaseMessage clientDisconnectedMessage, String[] subscribedTopics) {
	try {
	    mqttTransport = new MqttTransport(clientId, brokerUrl, clientDisconnectedTopic, clientDisconnectedMessage,
		    this, subscribedTopics);
	} catch (MqttException e) {
	    throw new RuntimeException(e);
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
	for (String topic : msg.getTopics()) {
	    try {
		mqttTransport.sendMessage(topic, msg.toString());
	    } catch (MqttPersistenceException e) {
		logger.error(e);
	    } catch (UnsupportedEncodingException e) {
		logger.error(e);
	    } catch (MqttException e) {
		throw new RuntimeException(e);
	    }
	    queueCount.incrementAndGet();
	}
    }

    @Override
    public void connectionLost(Throwable ex) {
	logger.error("CONNECTION LOST", ex);
    }

    @Override
    public void messageReceived(String topic, String message) {
	logger.debug("[RECEIVED] " + topic + "---" + message);
	try {
	    BaseMessage msg = deserializer.fromJsonMsg(message);
	    if (msg instanceof ClientBaseMessage) {
		msgHandler.handleClientMessage((ClientBaseMessage) msg);
	    } else if (msg instanceof ServerBaseMessage) {
		msgHandler.handleServerMessage((ServerBaseMessage) msg);
	    } else {
		logger.error("NON RECOGNIZED MSG: " + message);
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
