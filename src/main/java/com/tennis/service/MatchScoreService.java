package com.tennis.service;

import com.tennis.model.MatchScoreModel;
import com.tennis.model.Score;

public class MatchScoreService {
    private final MatchScoreCalculationService matchScoreCalculationService;

    public MatchScoreService(MatchScoreCalculationService matchScoreCalculationService) {
        this.matchScoreCalculationService = matchScoreCalculationService;
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
        matchScoreCalculationService.updateScoreState(player, opponent, currentMatch);
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
