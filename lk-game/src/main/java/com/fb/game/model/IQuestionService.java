package com.fb.game.model;

import java.util.Map;

import com.fb.messages.client.gameactions.Answer;
import com.fb.messages.server.gameactions.PlayerChallenged;

public interface IQuestionService {

    public String getNextQuestion(PlayerChallenged playerChallenged);

    public String computeWinner(Map<String, Answer> crtChallengePlayers);
}
