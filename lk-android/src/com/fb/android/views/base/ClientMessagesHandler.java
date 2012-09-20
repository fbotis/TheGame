package com.fb.android.views.base;

import com.fb.messages.server.ErrorMessage;
import com.fb.messages.server.gameactions.BeginChallenge;
import com.fb.messages.server.gameactions.BeginChooseTerritory;
import com.fb.messages.server.gameactions.ChallengeResult;
import com.fb.messages.server.gameactions.PlayerChallenged;
import com.fb.messages.server.gameactions.Question;
import com.fb.messages.server.gameactions.TerritoryChosen;
import com.fb.messages.server.general.Snapshot;
import com.fb.messages.server.room.GameCreated;
import com.fb.messages.server.room.GameStarted;
import com.fb.messages.server.room.UserJoinedGame;
import com.fb.messages.server.room.UserUnjoinedGame;

public interface ClientMessagesHandler {
    // *************** HANDLERS***************
    void handleTerritoryChosen(TerritoryChosen msg);

    void handleQuestion(Question msg);

    void handlePlayerChallenged(PlayerChallenged msg);

    void handleEndGame(BeginChallenge msg);

    void handleChallengeResult(ChallengeResult msg);

    void handleBeginChooseTerritory(BeginChooseTerritory msg);

    void handleBeginChallenge(BeginChallenge msg);

    void handleUserJoinedGame(UserJoinedGame msg);

    void handlerErrorMessage(ErrorMessage msg);

    void handlerUserUnjoinedGame(UserUnjoinedGame msg);

    void handleGameStarted(GameStarted msg);

    void handleNewGame(GameCreated msg);

    void handleSnapshot(Snapshot msg);
}
