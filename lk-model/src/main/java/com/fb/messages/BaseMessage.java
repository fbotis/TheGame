package com.fb.messages;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import com.fb.util.IDGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseMessage {
    private static Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC).create();

    private String type = getClass().getName();

    private String id = "Msg-" + IDGenerator.INSTANCE.nextID();

    protected Set<String> topics = new HashSet<String>();

    public String getType() {
	return type;
    }

    public String getId() {
	return id;
    }

    public Set<String> getTopics() {
	return topics;
    }

    public void addTopic(String topic) {
	topics.add(topic);
    }

    @Override
    public String toString() {
	return gson.toJson(this);
    }

}
