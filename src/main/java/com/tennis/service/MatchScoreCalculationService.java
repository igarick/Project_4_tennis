package com.tennis.service;

import com.tennis.entity.Match;
import com.tennis.model.MatchScoreModel;
import com.tennis.model.Score;
import com.tennis.service.point.PointIncrementRule;
import com.tennis.service.victory.GamesVictory;
import com.tennis.service.victory.PointsVictoryAndAdvantage;
import com.tennis.service.victory.SetsVictory;
import com.tennis.service.victory.TieBreak;
import com.tennis.util.EntitiesMapperAndBuilder;

public class MatchScoreCalculationService {
    private final PointIncrementRule pointIncrementRule;
    private final TieBreak tieBreak;
    private final PointsVictoryAndAdvantage pointsVictoryAndAdvantage;
    private final GamesVictory gamesVictory;
    private final SetsVictory setsVictory;
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    public MatchScoreCalculationService
            (PointIncrementRule pointIncrementRule,
             FinishedMatchesPersistenceService finishedMatchesPersistenceService,
             SetsVictory setsVictory,
             GamesVictory gamesVictory,
             PointsVictoryAndAdvantage pointsVictoryAndAdvantage,
             TieBreak tieBreak) {
        this.pointIncrementRule = pointIncrementRule;
        this.finishedMatchesPersistenceService = finishedMatchesPersistenceService;
        this.setsVictory = setsVictory;
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

        gamesVictory.handleGamesVictory(playerScore, opponentScore);

        setsVictory.handleSetsVictory(playerScore, opponentScore, currentMatch);

        if (currentMatch.getMatchModel().isFinished()) {

            Match match = Match.from(currentMatch);

            finishedMatchesPersistenceService.save(match);
        }
    }
}
