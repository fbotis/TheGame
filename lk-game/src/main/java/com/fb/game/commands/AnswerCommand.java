package com.fb.game.commands;

import com.fb.game.model.GameLogic;
import com.fb.messages.client.gameactions.Answer;
import com.fb.messages.server.gameactions.ChallengeResult;
import com.fb.transport.IServerMessageSender;

@MessageCommand(type = Answer.class)
public class AnswerCommand extends Command<Answer> {

    public AnswerCommand(GameLogic gameLogic, IServerMessageSender serverSender, Answer msg) {
	super(gameLogic, serverSender, msg);
    }

    @Override
    public void doWork() throws Exception {
	ChallengeResult res = getGameLogic().handleAnswer(getMessage());
	if (res != null) {
	    sendServerMessage(res);
	}
    }

}
