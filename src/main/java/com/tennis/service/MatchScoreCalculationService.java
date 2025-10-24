package com.tennis.service;

import com.tennis.model.*;
import com.tennis.service.point.PointIncrementRule;
import com.tennis.service.victory.*;
import com.tennis.entity.Match;
import com.tennis.entity.Player;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchScoreCalculationService {
    //    private static final PointIncrementRule pointIncrementRule = new PointIncrementRule();

    private final PointIncrementRule pointIncrementRule; // = new PointIncrementRule();
    private static final TieBreak tieBreak = new TieBreak();
    private static final PointsVictoryAndAdvantage pointsVictoryAndAdvantage = new PointsVictoryAndAdvantage();
    private static final GamesVictory gamesVictory = new GamesVictory();
    private static final SetsVictoryAndWinner setsVictoryAndWinner = new SetsVictoryAndWinner();
    private static final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

//    MatchScoreCalculationService service = new MatchScoreCalculationService(pointIncrementRule);

    public MatchScoreCalculationService(PointIncrementRule pointIncrementRule) {
        this.pointIncrementRule = pointIncrementRule;
    }

    public void updateCurrentPlayerScore(String firstPlayerIdParam, String secondPlayerIdParam, MatchScoreModel currentMatch) {
        Integer playerId = getPlayerId(firstPlayerIdParam, secondPlayerIdParam);

        Integer firstPlayerId = currentMatch.getMatchModel().getPlayer1().getId();

        Score player;
        Score opponent;
        if (playerId.equals(firstPlayerId)) {
            player = currentMatch.getFirstPlayerScore();
            opponent = currentMatch.getSecondPlayerScore();
        } else {
            player = currentMatch.getSecondPlayerScore();
            opponent = currentMatch.getFirstPlayerScore();
        }
        updateScoreState(player, opponent, currentMatch);
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

            PlayerModel player = setsVictoryAndWinner.determineWinner(currentMatch);
            currentMatch.getMatchModel().setWinner(player);

            Match match = new Match(
                    null,
                    new Player(
                            currentMatch.getMatchModel().getPlayer1().getId(),
                            currentMatch.getMatchModel().getPlayer1().getName()
                    ),
                    new Player(
                            currentMatch.getMatchModel().getPlayer2().getId(),
                            currentMatch.getMatchModel().getPlayer2().getName()
                    ),
                    new Player(
                            currentMatch.getMatchModel().getWinner().getId(),
                            currentMatch.getMatchModel().getWinner().getName()
                    )
            );
            finishedMatchesPersistenceService.save(match);
        }
    }

    private Integer getPlayerId(String firstPlayerIdParam, String secondPlayerIdParam) {
        int playerId;
        if (firstPlayerIdParam != null) {
            playerId = Integer.parseInt(firstPlayerIdParam);
        } else {
            playerId = Integer.parseInt(secondPlayerIdParam);
        }
        return playerId;
    }
}
