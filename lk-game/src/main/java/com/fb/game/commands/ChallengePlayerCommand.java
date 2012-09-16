package com.fb.game.commands;

import com.fb.game.model.GameLogic;
import com.fb.game.model.IQuestionService;
import com.fb.game.question.SimpleQuestionService;
import com.fb.messages.client.gameactions.ChallengePlayer;
import com.fb.messages.server.gameactions.PlayerChallenged;
import com.fb.messages.server.gameactions.Question;
import com.fb.transport.IServerMessageSender;

@MessageCommand(type = ChallengePlayer.class)
public class ChallengePlayerCommand extends Command<ChallengePlayer> {
    // TODO implement this
    private IQuestionService questionService = new SimpleQuestionService();

    public ChallengePlayerCommand(GameLogic gameLogic, IServerMessageSender serverSender, ChallengePlayer msg) {
	super(gameLogic, serverSender, msg);
    }

    @Override
    public void doWork() throws Exception {
	PlayerChallenged playerChallenged = getGameLogic().handleChallengePlayer(getMessage());
	sendServerMessage(playerChallenged);
	sendServerMessage(new Question(getMessage().getGameId(), questionService.getNextQuestion(playerChallenged)));
    }
}
