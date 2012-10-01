package com.fb.bot;

import java.util.Random;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.fb.bot.cmds.Command;
import com.fb.bot.cmds.CommandFactory;
import com.fb.messages.BaseMessage;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.ServerBaseMessage;
import com.fb.messages.client.ClientDisconnected;
import com.fb.messages.client.general.GetSnapshot;
import com.fb.topics.Topic;
import com.fb.transport.IMessageHandler;
import com.fb.transport.MessagesTransport;

public class BotService implements IMessageHandler {
    private static final Logger logger = Logger.getLogger(BotService.class);
    private static final String[] DEFAULT_SUB_TOPICS = { Topic.ALL_TOPIC, Topic.ROOMS_ENGINE, Topic.CLIENT_DISCONNECTED };
    private MessagesTransport msgTransport;
    private Bot bot;
    private CommandFactory cmdFact;

    public BotService(String clientId, String brokerUrl, String botName) throws MqttSecurityException, MqttException {
	msgTransport = new MessagesTransport(clientId, brokerUrl, Topic.CLIENT_DISCONNECTED, new ClientDisconnected(
		clientId), DEFAULT_SUB_TOPICS);
	msgTransport.setMsgHandler(this);
	msgTransport.subscribeToTopic(clientId);
	bot = new Bot(clientId, botName, msgTransport);
	cmdFact = new CommandFactory(bot);
	bot.sendMessage(new GetSnapshot(bot.getClientId()));
    }

    @Override
    public void handleClientMessage(ClientBaseMessage msg) {

    }

    @Override
    public void handleServerMessage(ServerBaseMessage msg) {
	Command<? extends BaseMessage> cmd = cmdFact.getCommand(msg);
	if (cmd != null) {
	    try {
		cmd.execute();
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	} else {
	    logger.error("Uknown command for msg " + msg);
	}
    }

    public static void main(String[] args) throws MqttSecurityException, MqttException {
	BotService srv = new BotService("BOT" + new Random().nextDouble(), "tcp://192.168.1.101:1883", "name");
    }
}
