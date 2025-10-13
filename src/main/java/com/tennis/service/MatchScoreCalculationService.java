package com.tennis.service;

import com.tennis.model.MatchScoreModel;

public class MatchScoreCalculationService {
    private static int POINT = 1;

    public void updateScore(String playerIdParam, MatchScoreModel currentMatch) {
        Integer playerId = Integer.parseInt(playerIdParam);

        Integer firstPlayerId = currentMatch.getMatch().getPlayer1().getId();
//        Integer secondPlayerId = currentMatch.getMatch().getPlayer2().getId();

        if (playerId.equals(firstPlayerId)) {

            int firstPlayerPointScore = currentMatch.getPointScore().getFirstPlayer();

            addPoint(firstPlayerPointScore, currentMatch);

            int firstPlayerSetScore = currentMatch.getSetScore().getFirstPlayer();
            int firstPlayerGameScore = currentMatch.getGameScore().getFirstPlayer();
            int updatedFirstPlayerPointScore = currentMatch.getPointScore().getFirstPlayer();

            int secondPlayerSetScore = currentMatch.getSetScore().getSecondPlayer();
            int secondPlayerGameScore = currentMatch.getGameScore().getSecondPlayer();
            int secondPlayerPointScore = currentMatch.getPointScore().getSecondPlayer();

            if (isSetVictory(updatedFirstPlayerPointScore, secondPlayerSetScore)) {
                return;
            }

            if (isGameVictory(updatedFirstPlayerPointScore, secondPlayerGameScore)) {
                return;
            }

            if (isPointVictory(updatedFirstPlayerPointScore, secondPlayerPointScore)) {
                return;
            }
        }

    }


    private void addPoint(int firstPlayerPointScore, MatchScoreModel currentMatch) {
        if (firstPlayerPointScore < 30) {
            currentMatch.getPointScore().setFirstPlayer(firstPlayerPointScore + 15);
        } else if (firstPlayerPointScore == 30) {
            currentMatch.getPointScore().setFirstPlayer(firstPlayerPointScore + 10);
        } else if (firstPlayerPointScore == 40) {
            currentMatch.getPointScore().setFirstPlayer(firstPlayerPointScore + 1);
        }
    }

    private boolean isPointVictory(int firstPlayerPointScore, int secondPlayerPointScore) {
        if ((firstPlayerPointScore > 40 && secondPlayerPointScore <= 30) ||

         (firstPlayerPointScore > 40 && firstPlayerPointScore - secondPlayerPointScore == 2));
        return true;
    }

    private boolean isGameVictory(int firstPlayerGameScore, int secondPlayerGameScore) {
        return (firstPlayerGameScore >= 6 && firstPlayerGameScore - secondPlayerGameScore == 2);
    }

    private boolean isSetVictory(int firstPlayerSetScore, int secondPlayerSetScore) {
        return (firstPlayerSetScore - secondPlayerSetScore == 2);
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
    - add point
    - checkers
        - point
        - game
        - set
    -
     */
}
