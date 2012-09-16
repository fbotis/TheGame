package com.fb.messages.client.gameactions;

public class Answer extends GameActionBaseMessage {

    private String answer;

    public Answer(String userId, String gameId, String answer) {
	super(userId, gameId);
	this.gameId = gameId;
	this.answer = answer;
    }

    public String getAnswer() {
	return answer;
    }
}
