package com.fb.android.mqtt;

import java.lang.ref.WeakReference;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

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
	    // TODO fix this
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
	// init all
	String clientId = getClientId();

	mqttTransport = new MessagesTransport(clientId, "tcp://192.168.1.101", Topic.CLIENT_DISCONNECTED,
		new ClientDisconnected(clientId), new String[] { Topic.ALL_TOPIC, clientId });
	super.onCreate();
    }

    public String getClientId() {
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

}
