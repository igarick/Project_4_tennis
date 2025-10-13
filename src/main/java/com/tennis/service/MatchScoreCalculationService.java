package com.tennis.service;

import com.tennis.model.MatchScoreModel;

public class MatchScoreCalculationService {
    public void calculate(String playerIdParam, MatchScoreModel currentMatch) {
        int playerId = Integer.parseInt(playerIdParam);

    }

    /*
    Логика:

    - распарсить playerIdParam -> playerId
    - сопоставить полученный ID с вложенными в currentMatch
        достать Match -> Player -> (firstPlayerId and secondPlayerID)

        if (playerId == firstPlayerId) {
        add point to Score (setScore, gameScore, pointScore) for firstPlayer
        }

        if (playerId == secondPlayerID) {
        add point to Score (setScore, gameScore, pointScore) for secondPlayer
        }
     */
}
