package com.fb.transport;

public interface ITransportListener {
    public void connectionLost(Throwable arg0);

    public void messageReceived(String topic, String message);

    public void messageSent();

}
