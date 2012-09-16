package com.fb.transport;

public interface ITransportService {

    void setTransportListener(ITransportListener transportListener);

    void unsuscribeFromTopic(String topic) throws Exception;

    void subscribeToTopic(String topic) throws Exception;

    void sendMessage(String topic, String message) throws Exception;
}
