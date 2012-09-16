package com.fb.game.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fb.exceptions.game.ChallengeInProgressException;
import com.fb.exceptions.game.InvalidStateException;
import com.fb.exceptions.game.InvalidTerritoryException;
import com.fb.exceptions.game.InvalidUserException;
import com.fb.exceptions.game.PlayerNotInGameException;
import com.fb.exceptions.game.TerritoryAlreadyChosenException;
import com.fb.game.question.SimpleQuestionService;
import com.fb.messages.ClientBaseMessage;
import com.fb.messages.client.gameactions.Answer;
import com.fb.messages.client.gameactions.ChallengePlayer;
import com.fb.messages.client.gameactions.ChooseTerritory;
import com.fb.messages.server.gameactions.ChallengeResult;
import com.fb.messages.server.gameactions.PlayerChallenged;
import com.fb.messages.server.gameactions.TerritoryChosen;

public class GameLogic {
    private final static String START_STATE = "START";
    private final static String CHOOSING_TERR_STATE = "CH_TERR";
    private final static String CHALLENGE_IN_PROGRESS = "CH_IN_PRG";
    private final static String CHALLENGE_PLAYER_STATE = "CH_PL";
    private final static String END_GAME = "END";

    private final GameMap map;
    private final List<String> players;;
    private Iterator<String> nextTurnIt;
    private final String gameId;
    private String crtState = START_STATE;

    private final Map<String, Answer> crtChallengePlayers = new HashMap<String, Answer>();
    private String crtChallengedTerritory;

    // TODO implement this
    private IQuestionService questionService = new SimpleQuestionService();

    public GameLogic(List<String> players, String gameId) {
	this.map = new GameMap(9);
	this.players = players;
	this.gameId = gameId;
	nextTurnIt = players.iterator();
    }

    public synchronized TerritoryChosen handleChooseTerritory(ChooseTerritory chooseTerr)
	    throws PlayerNotInGameException, TerritoryAlreadyChosenException, InvalidStateException {
	if (crtState.equals(START_STATE)) {
	    crtState = CHOOSING_TERR_STATE;
	}

	checkState(chooseTerr, CHOOSING_TERR_STATE);
	if (!players.contains(chooseTerr.getUserId())) {
	    throw new PlayerNotInGameException(chooseTerr);
	}

	if (map.isAssigned(chooseTerr.getTerritoryId())) {
	    throw new TerritoryAlreadyChosenException(chooseTerr);
	}

	map.assignTerritory(chooseTerr.getTerritoryId(), chooseTerr.getUserId());

	if (!map.isAnyTerritoryFree()) {
	    crtState = CHALLENGE_PLAYER_STATE;
	}

	return new TerritoryChosen(gameId, chooseTerr.getUserId(), chooseTerr.getTerritoryId());
    }

    public synchronized PlayerChallenged handleChallengePlayer(ChallengePlayer challengePlayer)
	    throws ChallengeInProgressException, InvalidStateException, InvalidTerritoryException {
	checkState(challengePlayer, CHALLENGE_PLAYER_STATE);
	if (!challengePlayer.getChallengedPlayerId().equals(map.getOwnerId(challengePlayer.getTerritoryId()))) {
	    throw new InvalidTerritoryException(challengePlayer);
	}

	crtChallengePlayers.put(challengePlayer.getUserId(), null);
	crtChallengePlayers.put(challengePlayer.getChallengedPlayerId(), null);
	crtChallengedTerritory = challengePlayer.getTerritoryId();

	crtState = CHALLENGE_IN_PROGRESS;

	return new PlayerChallenged(gameId, challengePlayer.getUserId(), challengePlayer.getChallengedPlayerId(),
		challengePlayer.getTerritoryId());
    }

    public synchronized ChallengeResult handleAnswer(Answer answer) throws InvalidStateException, InvalidUserException {
	checkState(answer, CHALLENGE_IN_PROGRESS);
	if (!crtChallengePlayers.keySet().contains(answer.getUserId())) {
	    throw new InvalidUserException(answer);
	}
	crtChallengePlayers.put(answer.getUserId(), answer);

	for (Answer answ : crtChallengePlayers.values()) {
	    if (answ == null) {
		return null;
	    }
	}

	ChallengeResult res = computeWinner(crtChallengePlayers);
	crtChallengePlayers.clear();
	crtChallengedTerritory = null;

	if (map.isAnyWinner()) {
	    crtState = END_GAME;
	} else {
	    crtState = CHALLENGE_PLAYER_STATE;
	}
	return res;

    }

    private ChallengeResult computeWinner(Map<String, Answer> crtChallengePlayers) {
	String winner = questionService.computeWinner(crtChallengePlayers);
	map.assignTerritory(crtChallengedTerritory, winner);
	// TODO Auto-generated method stub
	return new ChallengeResult(gameId, crtChallengePlayers.keySet().iterator().next());
    }

    private void checkState(ClientBaseMessage msg, String... possibleStates) throws InvalidStateException {
	if (!Arrays.asList(possibleStates).contains(crtState)) {
	    throw new InvalidStateException(msg, crtState, possibleStates);
	}
    }

    public String nextPlayer() {
	if (!nextTurnIt.hasNext()) {
	    nextTurnIt = players.iterator();
	}
	return nextTurnIt.next();
    }
}
