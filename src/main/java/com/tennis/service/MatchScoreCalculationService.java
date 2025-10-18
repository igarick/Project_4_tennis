package com.tennis.service;

import com.tennis.dto.MatchDto;
import com.tennis.model.*;
import com.tennis.service.point.PointIncrementRule;
import com.tennis.service.victory.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchScoreCalculationService {
    private static final PointIncrementRule pointIncrementRule = new PointIncrementRule();
    private static final TieBreak tieBreak = new TieBreak();
    private static final PointVictoryAndAdvantage pointVictory = new PointVictoryAndAdvantage();
    private static final GameVictory gameVictory = new GameVictory();
    private static final SetVictoryAndWinner setVictoryAndWinner = new SetVictoryAndWinner();
    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();


    public void calculate(String playerIdParam, MatchScoreModel currentMatch) {
        Integer playerId = Integer.parseInt(playerIdParam);

        Integer firstPlayerId = currentMatch.getMatch().getPlayer1().getId();
        Integer secondPlayerId = currentMatch.getMatch().getPlayer2().getId();

        if (playerId.equals(firstPlayerId)) {
            Score player = currentMatch.getFirstPlayerScore();
            Score opponent = currentMatch.getSecondPlayerScore();

            updateScoreState(player, opponent, currentMatch);
        }

        if (playerId.equals(secondPlayerId)) {
            Score player = currentMatch.getSecondPlayerScore();
            Score opponent = currentMatch.getFirstPlayerScore();

            updateScoreState(player, opponent, currentMatch);
        }
    }

    private void updateScoreState(Score playerScore, Score opponentScore, MatchScoreModel currentMatch) {
        pointIncrementRule.incrementPlayerPoint(playerScore, opponentScore, currentMatch);

        if (currentMatch.getMatch().isTieBreak()) {
            tieBreak.handleTieBreak(playerScore, opponentScore);
        } else {
            pointVictory.handleNormalGame(playerScore, opponentScore);
        }

        gameVictory.handleGameVictory(playerScore, opponentScore);

        boolean isSetVictory = setVictoryAndWinner.isSetVictory(playerScore.getSets(), opponentScore.getSets());
        if (isSetVictory) {
            currentMatch.getMatch().setFinished(true);

            Player player = setVictoryAndWinner.determineWinner(currentMatch);
            currentMatch.getMatch().setWinner(player);

            finishedMatchesPersistenceService.save(currentMatch.getMatch());
        }
    }
}
