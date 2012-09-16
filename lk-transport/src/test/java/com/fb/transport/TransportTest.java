package com.fb.transport;

import org.junit.Test;

import com.fb.messages.client.ClientDisconnected;

public class TransportTest {
    @Test
    public void testSmoke() throws Exception {
	MqttTransport transport = new MqttTransport(System.currentTimeMillis() + "sda", "tcp://localhost:1883",
		"disconnectedTopic", new ClientDisconnected("123"), new ITransportListener() {

		    @Override
		    public void messageSent() {
			System.out.println("SENT: ");

		    }

		    @Override
		    public void messageReceived(String topic, String message) {
			System.out.println("RECEIVED: " + topic + "-------" + message);

		    }

		    @Override
		    public void connectionLost(Throwable arg0) {
			arg0.printStackTrace();

		    }
		}, new String[] { "topic1", "topic2" });
	transport.sendMessage("topic1", "msg");
	transport.sendMessage("topic2", "msg");
	transport.sendMessage("topic3", "msg");

	Thread.sleep(100000000);
    }
}
