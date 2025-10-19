package com.tennis.service;

import com.tennis.model.*;
import com.tennis.service.point.PointIncrementRule;
import com.tennis.service.victory.*;
import com.tennis.entity.Match;
import com.tennis.entity.Player;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchScoreCalculationService {
    private static final PointIncrementRule pointIncrementRule = new PointIncrementRule();
    private static final TieBreak tieBreak = new TieBreak();
    private static final PointsVictoryAndAdvantage pointsVictoryAndAdvantage = new PointsVictoryAndAdvantage();
    private static final GamesVictory gamesVictory = new GamesVictory();
    private static final SetsVictoryAndWinner setsVictoryAndWinner = new SetsVictoryAndWinner();
    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();


    public void calculate(String playerIdParam, MatchScoreModel currentMatch) {
        Integer playerId = Integer.parseInt(playerIdParam);

        Integer firstPlayerId = currentMatch.getMatchModel().getPlayer1().getId();
        Integer secondPlayerId = currentMatch.getMatchModel().getPlayer2().getId();

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

        if (currentMatch.getMatchModel().isTieBreak()) {
            tieBreak.handleTieBreak(playerScore, opponentScore);
        } else {
            pointsVictoryAndAdvantage.handleNormalGame(playerScore, opponentScore);
        }

        gamesVictory.handleGameVictory(playerScore, opponentScore);

        boolean victory = setsVictoryAndWinner.isSetVictory(playerScore.getSets(), opponentScore.getSets());
        if (victory) {
            currentMatch.getMatchModel().setFinished(true);

            Player player = setsVictoryAndWinner.determineWinner(currentMatch);
            currentMatch.getMatchModel().setWinner(player);

            Match match = new Match(
                    null,
                    currentMatch.getMatchModel().getPlayer1(),
                    currentMatch.getMatchModel().getPlayer2(),
                    currentMatch.getMatchModel().getWinner()
            );

            finishedMatchesPersistenceService.save(match);

        }
    }
}
