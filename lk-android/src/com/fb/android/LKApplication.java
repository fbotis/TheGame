package com.fb.android;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.fb.android.mqtt.MqttService;
import com.fb.android.mqtt.MqttService.LocalBinder;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.ServerBaseMessage;
import com.fb.transport.IMessageHandler;

public class LKApplication extends Application implements IMessageHandler {
    private static final String logTag = "LKApplication";
    private MqttService mService;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

	private boolean mBound;

	@Override
	public void onServiceConnected(ComponentName className, IBinder service) {
	    // We've bound to LocalService, cast the IBinder and get
	    // LocalService instance
	    LocalBinder<MqttService> binder = (LocalBinder<MqttService>) service;
	    mService = (MqttService) binder.getService();
	    mService.setMsgHandler(LKApplication.this);
	    mBound = true;
	    sendPendingMessages();
	    subscribeToPendingTopics();
	    Log.d(logTag, "Service bound!");
	}

	@Override
	public void onServiceDisconnected(ComponentName arg0) {
	    mBound = false;
	}
    };

    private Set<IMessageHandler> handlers = new HashSet<IMessageHandler>();
    private LinkedList<ServerBaseMessage> unconsumedMessages = new LinkedList<ServerBaseMessage>();
    private LinkedList<ClientBaseMessage> pendingMessages = new LinkedList<ClientBaseMessage>();
    private LinkedList<String> pendingTopics = new LinkedList<String>();

    private void sendPendingMessages() {
	Iterator<ClientBaseMessage> pendingIt = pendingMessages.iterator();
	while (pendingIt.hasNext()) {
	    this.mService.sendMessage(pendingIt.next());
	}
    }

    protected void subscribeToPendingTopics() {
	for (String t : pendingTopics) {
	    mService.subscribeToTopic(t);
	}

    }

    @Override
    public void onCreate() {
	Log.d(logTag, "Binding to service");
	bindService(new Intent(this, MqttService.class), mConnection, Context.BIND_AUTO_CREATE);
	super.onCreate();
    }

    @Override
    public void handleClientMessage(ClientBaseMessage msg) {
	// this shouldn't be called
	Log.e(logTag, msg.toString());

    }

    @Override
    public void handleServerMessage(ServerBaseMessage msg) {
	if (handlers.size() == 0) {
	    unconsumedMessages.addFirst(msg);
	}
	notifyHandlers(msg);
    }

    private void notifyUnconsumedMsg() {
	Iterator<ServerBaseMessage> unconsumedIt = unconsumedMessages.iterator();
	while (unconsumedIt.hasNext()) {
	    notifyHandlers(unconsumedIt.next());
	    unconsumedIt.remove();
	}
    }

    private void notifyHandlers(ServerBaseMessage msg) {
	Iterator<IMessageHandler> it = handlers.iterator();
	while (it.hasNext()) {
	    it.next().handleServerMessage(msg);
	}
    }

    public void addMessageHandler(IMessageHandler handler) {
	handlers.add(handler);
	notifyUnconsumedMsg();
    }

    public void removeMessageHandler(IMessageHandler handler) {
	this.handlers.remove(handler);
    }

    public void sendClientMsg(ClientBaseMessage msg) {
	// TODO fix this
	if (mService != null) {
	    this.mService.sendMessage(msg);
	} else {
	    pendingMessages.add(msg);
	}
    }

    // TODO modify this
    private static volatile String clientID = "mobile" + System.nanoTime();;

    public String getClientId() {
	return clientID;
    }

    public void subscribeToTopic(String topic) {
	if (mService != null) {
	    mService.subscribeToTopic(topic);
	} else {
	    pendingTopics.add(topic);
	}
    }

    public void unsubscribeFromTopic(String topic) {
	if (mService != null) {
	    mService.unsubscribeFromTopic(topic);
	}
    }

}
