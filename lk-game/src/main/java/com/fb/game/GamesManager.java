package com.fb.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fb.game.model.GameLogic;
import com.fb.messages.intercomponents.NewGame;

public class GamesManager {
    // TODO remove end games
    private Map<String, GameLogic> currentGames = new ConcurrentHashMap<String, GameLogic>();

    public GameLogic getGameLogic(String gameId) {
	return currentGames.get(gameId);
    }

    public GameLogic createNewGame(NewGame newGame) {
	GameLogic gameLogic = new GameLogic(newGame.getRoomUsers(), newGame.getRoomId(), newGame.getMap());
	currentGames.put(newGame.getRoomId(), gameLogic);
	return gameLogic;
    }

}
