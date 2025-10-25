package com.tennis.service;

import com.tennis.entity.Match;
import com.tennis.entity.Player;
import com.tennis.model.MatchScoreModel;
import com.tennis.model.PlayerModel;
import com.tennis.model.Score;
import com.tennis.service.point.PointIncrementRule;
import com.tennis.service.victory.GamesVictory;
import com.tennis.service.victory.PointsVictoryAndAdvantage;
import com.tennis.service.victory.SetsVictoryAndWinner;
import com.tennis.service.victory.TieBreak;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchScoreCalculationService {
    //   private static final PointIncrementRule pointIncrementRule = new PointIncrementRule();

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

    public void updateScoreState(Score playerScore, Score opponentScore, MatchScoreModel currentMatch) {
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

            Match match = buildMatch(currentMatch);

            finishedMatchesPersistenceService.save(match);
        }
    }

    private Match buildMatch(MatchScoreModel currentMatch) {
        return new Match(
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
    }
}
