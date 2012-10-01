package com.fb.game;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.fb.exceptions.game.InvalidStateException;
import com.fb.exceptions.game.InvalidTerritoryException;
import com.fb.exceptions.game.InvalidUserException;
import com.fb.exceptions.game.PlayerNotInGameException;
import com.fb.exceptions.game.TerritoryAlreadyChosenException;
import com.fb.game.model.GameLogic;
import com.fb.messages.client.gameactions.Answer;
import com.fb.messages.client.gameactions.ChallengePlayer;
import com.fb.messages.client.gameactions.ChooseTerritory;
import com.fb.messages.server.gameactions.ChallengeResult;
import com.fb.messages.server.gameactions.PlayerChallenged;
import com.fb.messages.server.gameactions.TerritoryChosen;

public class GameLogicTest {
    @Test
    public void testSmoke() throws Exception {
	String gameId = "gameId";
	String u1 = "u1";
	String u2 = "u2";
	String u3 = "u3";
	Exception e = null;
	List<String> users = new ArrayList<String>();
	users.add(u1);
	users.add(u2);
	users.add(u3);

	List<String> mapIds = new ArrayList<String>();
	for (int i = 0; i < 9; i++) {
	    mapIds.add(String.valueOf(i));
	}
	GameLogic game = new GameLogic(users, gameId, mapIds);

	ChooseTerritory u1Ch1 = new ChooseTerritory(u1, gameId, "0");
	ChooseTerritory u2Ch1 = new ChooseTerritory(u2, gameId, "1");
	ChooseTerritory u3Ch1 = new ChooseTerritory(u3, gameId, "2");
	ChooseTerritory u1Ch2 = new ChooseTerritory(u1, gameId, "3");
	ChooseTerritory u2Ch2 = new ChooseTerritory(u2, gameId, "4");
	ChooseTerritory u3Ch2 = new ChooseTerritory(u3, gameId, "5");
	ChooseTerritory u1Ch3 = new ChooseTerritory(u1, gameId, "6");
	ChooseTerritory u2Ch3 = new ChooseTerritory(u2, gameId, "7");
	ChooseTerritory u3Ch3 = new ChooseTerritory(u3, gameId, "8");

	TerritoryChosen tChosen = game.handleChooseTerritory(u1Ch1);
	Assert.assertEquals(u1, tChosen.getTerritoryUserId());
	Assert.assertEquals("0", tChosen.getTerritoryId());

	tChosen = game.handleChooseTerritory(u2Ch1);
	Assert.assertEquals(u2, tChosen.getTerritoryUserId());
	Assert.assertEquals("1", tChosen.getTerritoryId());

	tChosen = game.handleChooseTerritory(u3Ch1);
	Assert.assertEquals(u3, tChosen.getTerritoryUserId());
	Assert.assertEquals("2", tChosen.getTerritoryId());

	tChosen = game.handleChooseTerritory(u1Ch2);
	Assert.assertEquals(u1, tChosen.getTerritoryUserId());
	Assert.assertEquals("3", tChosen.getTerritoryId());

	tChosen = game.handleChooseTerritory(u2Ch2);
	Assert.assertEquals(u2, tChosen.getTerritoryUserId());
	Assert.assertEquals("4", tChosen.getTerritoryId());

	tChosen = game.handleChooseTerritory(u3Ch2);
	Assert.assertEquals(u3, tChosen.getTerritoryUserId());
	Assert.assertEquals("5", tChosen.getTerritoryId());

	tChosen = game.handleChooseTerritory(u1Ch3);
	Assert.assertEquals(u1, tChosen.getTerritoryUserId());
	Assert.assertEquals("6", tChosen.getTerritoryId());

	tChosen = game.handleChooseTerritory(u2Ch3);
	Assert.assertEquals(u2, tChosen.getTerritoryUserId());
	Assert.assertEquals("7", tChosen.getTerritoryId());

	try {
	    tChosen = game.handleChooseTerritory(new ChooseTerritory("sfsa", gameId, "214124"));
	} catch (Exception e1) {
	    e = e1;
	}

	Assert.assertEquals(PlayerNotInGameException.class, e.getClass());

	try {
	    tChosen = game.handleChooseTerritory(new ChooseTerritory("u1", gameId, "7"));
	} catch (Exception e1) {
	    e = e1;
	}

	Assert.assertEquals(TerritoryAlreadyChosenException.class, e.getClass());

	tChosen = game.handleChooseTerritory(u3Ch3);
	Assert.assertEquals(u3, tChosen.getTerritoryUserId());
	Assert.assertEquals("8", tChosen.getTerritoryId());

	try {
	    tChosen = game.handleChooseTerritory(u3Ch3);
	} catch (Exception e1) {
	    e = e1;
	}

	Assert.assertEquals(InvalidStateException.class, e.getClass());

	PlayerChallenged plChallenged;
	ChallengePlayer chPlayer = new ChallengePlayer(u1, gameId, u2, "2");

	try {
	    plChallenged = game.handleChallengePlayer(chPlayer);
	} catch (Exception e1) {
	    e = e1;
	}

	Assert.assertEquals(InvalidTerritoryException.class, e.getClass());

	chPlayer = new ChallengePlayer(u1, gameId, u2, "0");

	try {
	    plChallenged = game.handleChallengePlayer(chPlayer);
	} catch (Exception e1) {
	    e = e1;
	}
	Assert.assertEquals(InvalidTerritoryException.class, e.getClass());

	chPlayer = new ChallengePlayer(u1, gameId, u2, "1");
	plChallenged = game.handleChallengePlayer(chPlayer);
	Assert.assertEquals(plChallenged.getChallengedPlayerId(), chPlayer.getChallengedPlayerId());
	Assert.assertEquals(plChallenged.getChallengerId(), chPlayer.getUserId());
	Assert.assertEquals(plChallenged.getTerritoryId(), chPlayer.getTerritoryId());

	try {
	    plChallenged = game.handleChallengePlayer(chPlayer);
	} catch (Exception e1) {
	    e = e1;
	}
	Assert.assertEquals(InvalidStateException.class, e.getClass());

	Answer answer1 = new Answer(u1, gameId, "");
	Answer answer2 = new Answer(u2, gameId, "");
	Answer answer3 = new Answer(u3, gameId, "");

	ChallengeResult chRes;
	try {
	    chRes = game.handleAnswer(answer3);
	} catch (Exception e1) {
	    e = e1;
	}
	Assert.assertEquals(InvalidUserException.class, e.getClass());

	chRes = game.handleAnswer(answer1);
	Assert.assertEquals(null, chRes);
	chRes = game.handleAnswer(answer2);
	Assert.assertNotNull(chRes);

    }
}
