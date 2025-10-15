package com.tennis.service;

import com.tennis.model.MatchScoreModel;
import com.tennis.model.PointScoreEnum;
import com.tennis.model.Score;

public class MatchScoreCalculationService {
    private static final PointScoreEnum FORTY = PointScoreEnum.FORTY;
    private static final PointScoreEnum ADVANTAGE = PointScoreEnum.ADVANTAGE;

    public void updateScore(String playerIdParam, MatchScoreModel currentMatch) {
        Integer playerId = Integer.parseInt(playerIdParam);

        Integer firstPlayerId = currentMatch.getMatch().getPlayer1().getId();
        Integer secondPlayerId = currentMatch.getMatch().getPlayer2().getId();

        if (playerId.equals(firstPlayerId)) {
            Score firstPlayerScore = currentMatch.getFirstPlayerScore();
            Score secondPlayerScore = currentMatch.getSecondPlayerScore();
//            PointScoreEnum firstPlayerPoint = currentMatch.getFirstPlayerScore().getPoints();
//            PointScoreEnum secondPlayerPoint = currentMatch.getSecondPlayerPoints();

//            addPoint(firstPlayerScore, currentMatch);
            update(firstPlayerScore, secondPlayerScore, currentMatch);
        }

        if (playerId.equals(secondPlayerId)) {
            Score firstPlayerScore = currentMatch.getSecondPlayerScore();
            Score secondPlayerScore = currentMatch.getFirstPlayerScore();
//            PointScoreEnum firstPlayerPoint = currentMatch.getSecondPlayerPoints();
//            PointScoreEnum secondPlayerPoint = currentMatch.getSecondPlayerScore().getPoints();

//            addPoint(secondPlayerPoint, currentMatch);
//            update(firstPlayerPoint, secondPlayerPoint, currentMatch);
//            addPoint(firstPlayerScore, currentMatch);
            update(firstPlayerScore, secondPlayerScore, currentMatch);


        }
    }
//    private void update(PointScoreEnum playerPoints,PointScoreEnum opponentPoints, MatchScoreModel currentMatch) {

    //     private void update(Score firstPlayerScore,Score secondPlayerScore, MatchScoreModel currentMatch) {
    private void update(Score playerScore, Score opponentScore, MatchScoreModel currentMatch) {

        addPoint(playerScore);

        int playerScoreSets = playerScore.getSets();
        int playerScoreGames = playerScore.getGames();
        PointScoreEnum playerScorePoints = playerScore.getPoints();

        int opponentScoreSets = opponentScore.getSets();
        int opponentScoreGames = opponentScore.getGames();
        PointScoreEnum opponentScorePoints = opponentScore.getPoints();


        if (isDeuce(playerScorePoints, opponentScorePoints)) {
            playerScore.setPoints(FORTY);
            opponentScore.setPoints(FORTY);
            return;
        }

        if (isPointVictory(playerScorePoints, opponentScorePoints)) {
            playerScore.setPoints(PointScoreEnum.LOVE);
            opponentScore.setPoints(PointScoreEnum.LOVE);

            updateScoreGames(playerScore, playerScoreGames);
        }

        if (isGameVictory(playerScoreGames, opponentScoreGames)) {

            System.out.println("--*-*-*-*-*-*-*-*-*-*-*-*-*- win *- *-*-*-*-*- *-*- *-* -*");
            return;
        }

//        if (isSetVictory(playerScorePoints, opponentScorePoints)) {
//            // redirect to winPage
//            return;
//        }
    }

    private void updateScoreGames(Score playerScore, int playerScoreGames) {
        int updatedScoreGame = playerScoreGames + 1;
        playerScore.setGames(updatedScoreGame);
    }

    private void addPoint(Score playerScore) {      //PointScoreEnum firstPlayerPoint
        PointScoreEnum point = playerScore.getPoints();
        switch (point) {
            case LOVE:
                playerScore.setPoints(PointScoreEnum.FIFTEEN);
                break;
            case FIFTEEN:
                playerScore.setPoints(PointScoreEnum.THIRTY);
                break;
            case THIRTY:
                playerScore.setPoints(PointScoreEnum.FORTY);
                break;
            case FORTY:
                playerScore.setPoints(PointScoreEnum.ADVANTAGE);
                break;
            case ADVANTAGE:
                playerScore.setPoints(PointScoreEnum.WIN);
                break;
            default:
                playerScore.setPoints(point);
        }
    }


    private boolean isDeuce(PointScoreEnum playerScore, PointScoreEnum opponentScore) {
        return (playerScore == ADVANTAGE && opponentScore == ADVANTAGE);
    }

    private boolean isPointVictory(PointScoreEnum playerScore, PointScoreEnum opponentScore) {
        return  ((playerScore == PointScoreEnum.ADVANTAGE && opponentScore != FORTY) ||
        (playerScore == PointScoreEnum.WIN && opponentScore == FORTY));
    }

    private boolean isGameVictory(int playerGameScore, int opponentGameScore) {
        if (playerGameScore == 6 || playerGameScore == 7 && playerGameScore - opponentGameScore == 2) {
            return true;
        }
        return false;
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
