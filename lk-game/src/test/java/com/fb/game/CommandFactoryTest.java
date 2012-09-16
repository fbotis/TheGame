package com.fb.game;

import junit.framework.Assert;

import org.junit.Test;

import com.fb.game.commands.AnswerCommand;
import com.fb.game.commands.ChallengePlayerCommand;
import com.fb.game.commands.ChooseTerritoryCommand;
import com.fb.game.commands.CommandFactory;
import com.fb.messages.ServerBaseMessage;
import com.fb.messages.client.gameactions.Answer;
import com.fb.messages.client.gameactions.ChallengePlayer;
import com.fb.messages.client.gameactions.ChooseTerritory;
import com.fb.transport.IServerMessageSender;

public class CommandFactoryTest {

    @Test
    public void testSmoke() throws Exception {
	CommandFactory fact = new CommandFactory(new GamesManager(), new IServerMessageSender() {

	    @Override
	    public void sendServerMessage(ServerBaseMessage arg0) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void subscribeToTopic(String topic) throws Exception {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void unsubscribeFromTopic(String topic) throws Exception {
		// TODO Auto-generated method stub

	    }
	});

	Assert.assertEquals(ChooseTerritoryCommand.class, fact.getCommand(new ChooseTerritory("", "", "")).getClass());
	Assert.assertEquals(ChallengePlayerCommand.class, fact.getCommand(new ChallengePlayer("", "", "", ""))
		.getClass());
	Assert.assertEquals(AnswerCommand.class, fact.getCommand(new Answer("", "", "")).getClass());
    }
}
