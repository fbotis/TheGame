package com.fb.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.fb.android.mqtt.MqttService;
import com.fb.android.mqtt.MqttService.LocalBinder;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.ServerBaseMessage;
import com.fb.messages.client.general.GetSnapshot;
import com.fb.messages.client.room.CreateGame;
import com.fb.transport.IMessageHandler;

public class LKActivity extends Activity implements IMessageHandler {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	getApplicationContext().bindService(new Intent(this, MqttService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

	private MqttService mService;
	private boolean mBound;

	@Override
	public void onServiceConnected(ComponentName className, IBinder service) {
	    // We've bound to LocalService, cast the IBinder and get
	    // LocalService instance
	    LocalBinder binder = (LocalBinder) service;
	    mService = (MqttService) binder.getService();
	    mService.setMsgHandler(LKActivity.this);
	    mService.sendMessage(new GetSnapshot(mService.getClientId()));
	    mBound = true;
	}

	@Override
	public void onServiceDisconnected(ComponentName arg0) {
	    mBound = false;
	}
    };

    @Override
    public void handleClientMessage(final ClientBaseMessage arg0) {
	this.runOnUiThread(new Runnable() {

	    @Override
	    public void run() {
		Toast.makeText(getApplicationContext(), arg0.toString(), Toast.LENGTH_LONG).show();
		Log.d("t", arg0.toString());
	    }
	});

    }

    @Override
    public void handleServerMessage(final ServerBaseMessage arg0) {
	this.runOnUiThread(new Runnable() {

	    @Override
	    public void run() {
		Toast.makeText(getApplicationContext(), arg0.toString(), Toast.LENGTH_LONG).show();
		Log.d("t", arg0.toString());
	    }
	});

    }
}