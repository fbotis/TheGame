package com.fb.game.question;

import java.util.Map;

import com.fb.game.model.IQuestionService;
import com.fb.messages.client.gameactions.Answer;
import com.fb.messages.server.gameactions.PlayerChallenged;

public class SimpleQuestionService implements IQuestionService {

    
    public SimpleQuestionService() {
	// TODO Auto-generated constructor stub
    }
    
    // TODO Implement this
    @Override
    public String getNextQuestion(PlayerChallenged playerChallenged) {
	return "";
    }

    // TODO implement
    @Override
    public String computeWinner(Map<String, Answer> crtChallengePlayers) {
	return crtChallengePlayers.keySet().iterator().next();
    }

}
