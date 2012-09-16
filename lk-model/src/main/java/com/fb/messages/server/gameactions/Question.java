package com.fb.messages.server.gameactions;

import com.fb.messages.ServerBaseMessage;

public class Question extends ServerBaseMessage {
    private String gameId;
    private String question;

    public Question(String gameId, String question) {
	super("S" + gameId);
	this.gameId = gameId;
	this.question = question;
    }

    public String getGameId() {
	return gameId;
    }

    public String getQuestion() {
	return question;
    }

}
