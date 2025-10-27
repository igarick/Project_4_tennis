package com.tennis.service;

import com.tennis.entity.Match;
import com.tennis.model.MatchScoreModel;
import com.tennis.model.PlayerModel;
import com.tennis.model.Score;
import com.tennis.service.point.PointIncrementRule;
import com.tennis.service.victory.GamesVictory;
import com.tennis.service.victory.PointsVictoryAndAdvantage;
import com.tennis.service.victory.SetsVictoryAndWinner;
import com.tennis.service.victory.TieBreak;
import com.tennis.util.EntitiesMapperAndBuilder;

public class MatchScoreCalculationService {
    private final PointIncrementRule pointIncrementRule;
    private final TieBreak tieBreak;
    private final PointsVictoryAndAdvantage pointsVictoryAndAdvantage;
    private final GamesVictory gamesVictory;
    private final SetsVictoryAndWinner setsVictoryAndWinner;
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    public MatchScoreCalculationService
            (PointIncrementRule pointIncrementRule,
             FinishedMatchesPersistenceService finishedMatchesPersistenceService,
             SetsVictoryAndWinner setsVictoryAndWinner,
             GamesVictory gamesVictory,
             PointsVictoryAndAdvantage pointsVictoryAndAdvantage,
             TieBreak tieBreak) {
        this.pointIncrementRule = pointIncrementRule;
        this.finishedMatchesPersistenceService = finishedMatchesPersistenceService;
        this.setsVictoryAndWinner = setsVictoryAndWinner;
        this.gamesVictory = gamesVictory;
        this.pointsVictoryAndAdvantage = pointsVictoryAndAdvantage;
        this.tieBreak = tieBreak;
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

            Match match = EntitiesMapperAndBuilder.buildMatch(currentMatch);

            finishedMatchesPersistenceService.save(match);
        }
    }
}
