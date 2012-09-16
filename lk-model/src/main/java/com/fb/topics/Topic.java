package com.fb.topics;

public interface Topic {
    // topics on which clients put messages
    String ROOMS_ENGINE = "rooms";
    String CLIENT_DISCONNECTED = "clientDisconnected";
    // topics on which server puts messages
    String ALL_TOPIC = "all";
    String INTER_COMPONENT_GAME_ENGINE = "gameEngine";

}
