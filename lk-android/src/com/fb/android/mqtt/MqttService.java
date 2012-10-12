package com.fb.android.mqtt;

import java.lang.ref.WeakReference;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.fb.android.Config;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.client.ClientDisconnected;
import com.fb.topics.Topic;
import com.fb.transport.IMessageHandler;
import com.fb.transport.MessagesTransport;

public class MqttService extends Service {
    private MessagesTransport mqttTransport;

    public class LocalBinder<S> extends Binder {
	private WeakReference<S> mService;

	public LocalBinder(S service) {
	    mService = new WeakReference<S>(service);
	}

	public S getService() {
	    return mService.get();
	}

	public void close() {
	    mService = null;
	}
    }

    public class AsyncSendMessage extends AsyncTask<ClientBaseMessage, Integer, Boolean> {

	@Override
	protected Boolean doInBackground(ClientBaseMessage... params) {
	    if (!mqttTransport.isConnected()) {
		createTransport();
	    }
	    // TODO fix this (subscribe to user topic somewhere else)
	    if (params.length > 0) {
		try {
		    mqttTransport.subscribeToTopic(params[0].getUserId());
		} catch (MqttSecurityException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (MqttException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    for (ClientBaseMessage msg : params) {
		mqttTransport.sendClientMessage(msg);
		// TODO exceptions from sendClient?
	    }
	    return true;
	}
    }

    final LocalBinder<MqttService> binder = new LocalBinder<MqttService>(this);
    private String clientId;

    @Override
    public IBinder onBind(Intent intent) {
	Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
	return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	// TODO Auto-generated method stub
	return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
	// init transport
	createTransport();
	super.onCreate();
    }

    private void createTransport() {
	Log.d("MqttServoce", "Creating transport, connecting");
	mqttTransport = new MessagesTransport(getClientId(), Config.INSTANCE.getMQTTBrokerURL(),
		Topic.CLIENT_DISCONNECTED, new ClientDisconnected(clientId), new String[] { Topic.ALL_TOPIC, clientId });
    }

    public String getClientId() {
	//TODO change this with a real id
	if (this.clientId == null) {
	    this.clientId = "mobile" + System.currentTimeMillis();
	}
	// TODO Auto-generated method stub
	return clientId;
    }

    public void setMsgHandler(IMessageHandler msgHandler) {
	mqttTransport.setMsgHandler(msgHandler);
    }

    public void sendMessage(ClientBaseMessage msg) {
	new AsyncSendMessage().execute(msg);
    }

    public void subscribeToTopic(String topic) {
	if (!mqttTransport.isConnected()) {
	    createTransport();
	}
	try {
	    mqttTransport.subscribeToTopic(topic);
	} catch (MqttSecurityException e) {
	    Log.e("MqttService", "Security error", e);
	} catch (MqttException e) {
	    Log.e("MqttService", "MQT exception", e);
	}
    }

    public void unsubscribeFromTopic(String topic) {
	if (!mqttTransport.isConnected()) {
	    createTransport();
	}
	try {
	    mqttTransport.unsubscribeFromTopic(topic);
	} catch (MqttException e) {
	    Log.e("MqttService", "MQT exception", e);
	}
    }

}
