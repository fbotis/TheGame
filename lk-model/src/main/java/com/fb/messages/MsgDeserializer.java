package com.fb.messages;

import com.google.gson.Gson;

public class MsgDeserializer {
    private Gson gson = new Gson();

    public BaseMessage fromJsonMsg(String jsonMsg) throws ClassNotFoundException {
	BaseMessage msg = gson.fromJson(jsonMsg, ClientBaseMessage.class);
	String type = msg.getType();
	Class<?> clasz = getClass().getClassLoader().loadClass(type);
	return (BaseMessage) gson.fromJson(jsonMsg, clasz);
    }

}
