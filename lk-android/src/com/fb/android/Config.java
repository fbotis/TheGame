package com.fb.android;

public enum Config {
    INSTANCE;

    public String getMQTTBrokerURL() {
	return "tcp://192.168.1.101:1883";
    }
}
