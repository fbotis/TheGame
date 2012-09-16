package com.fb.transport;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

import com.fb.messages.BaseMessage;

public class MqttTransport implements MqttCallback, ITransportService {
    private static final Logger logger = Logger.getLogger(MqttTransport.class);

    private String mqttBrokerUrl;
    // TODO see what's this
    private MqttClientPersistence persistence;
    private MqttConnectOptions connectionOptions;
    private MqttClient mqttClient;
    private ITransportListener transportListener;

    public MqttTransport(String clientId, String brokerUrl, String clientDisconnectedTopic,
	    BaseMessage clientDisconnectedMessage, ITransportListener transportListener, String[] subscribedTopics)
	    throws MqttException {
	this.transportListener = transportListener;
	this.mqttBrokerUrl = brokerUrl;
	// CONN OPTIONS
	connectionOptions = new MqttConnectOptions();
	connectionOptions.setCleanSession(false);
	// in seconds
	connectionOptions.setConnectionTimeout(60);
	connectionOptions.setKeepAliveInterval(30);

	// pers
	persistence = new MemoryPersistence();
	mqttClient = new MqttClient(mqttBrokerUrl, clientId, persistence);
	MqttTopic disconnectedTopic = mqttClient.getTopic(clientDisconnectedTopic);
	try {
	    connectionOptions.setWill(disconnectedTopic, clientDisconnectedMessage.toString().getBytes("UTF-8"), 2,
		    false);
	} catch (UnsupportedEncodingException e) {
	    logger.error("Error notifying setting WILL", e);
	}
	mqttClient.setCallback(this);
	mqttClient.connect(connectionOptions);
	mqttClient.subscribe(subscribedTopics);
    }

    @Override
    public void connectionLost(Throwable arg0) {
	transportListener.connectionLost(arg0);
    }

    @Override
    public void deliveryComplete(MqttDeliveryToken token) {
	transportListener.messageSent();
    }

    @Override
    public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {
	transportListener.messageReceived(topic.getName(), new String(message.getPayload(), "UTF-8"));
    }

    public void sendMessage(String topic, String message) throws Exception {
	mqttClient.getTopic(topic).publish(message.getBytes("UTF-8"), 2, false);
    }

    public void subscribeToTopic(String topic) throws MqttSecurityException, MqttException {
	mqttClient.subscribe(topic, 2);
    }

    public void unsuscribeFromTopic(String topic) throws MqttException {
	mqttClient.unsubscribe(topic);
    }

    public void setTransportListener(ITransportListener transportListener) {
	this.transportListener = transportListener;
    }
}
