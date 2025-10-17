package com.tennis.service;

import com.tennis.model.MatchScoreModel;
import com.tennis.model.PointScoreEnum;
import com.tennis.model.Score;

public class MatchScoreCalculationService {
    private static final PointScoreEnum FORTY = PointScoreEnum.FORTY;
    private static final PointScoreEnum ADVANTAGE = PointScoreEnum.ADVANTAGE;

//    private static boolean isTieBreakGlobal;

    public void updateScore(String playerIdParam, MatchScoreModel currentMatch) {
        Integer playerId = Integer.parseInt(playerIdParam);

        Integer firstPlayerId = currentMatch.getMatch().getPlayer1().getId();
        Integer secondPlayerId = currentMatch.getMatch().getPlayer2().getId();

        if (playerId.equals(firstPlayerId)) {
            Score firstPlayerScore = currentMatch.getFirstPlayerScore();
            Score secondPlayerScore = currentMatch.getSecondPlayerScore();

            addPoint(firstPlayerScore, secondPlayerScore, currentMatch);

            update(firstPlayerScore, secondPlayerScore, currentMatch);
        }

        if (playerId.equals(secondPlayerId)) {
            Score firstPlayerScore = currentMatch.getSecondPlayerScore();
            Score secondPlayerScore = currentMatch.getFirstPlayerScore();

            addPoint(firstPlayerScore, secondPlayerScore, currentMatch);

            update(firstPlayerScore, secondPlayerScore, currentMatch);
        }
    }

    private void addPoint(Score playerScore, Score opponentScore, MatchScoreModel currentMatch) {
        if (isTieBreak(playerScore, opponentScore)) {
            currentMatch.setTieBreak(true);
            addTieBreakPoint(playerScore);
        } else {
            currentMatch.setTieBreak(false);
            addMatchPoint(playerScore);
        }
    }

    private void update(Score playerScore, Score opponentScore, MatchScoreModel currentMatch) {
        if (isTieBreakVictory(playerScore.getTieBreakPoints(), opponentScore.getTieBreakPoints())) {
            playerScore.setTieBreakPoints(0);
            opponentScore.setTieBreakPoints(0);

            updateScoreGames(playerScore, playerScore.getGames());
        }

        int playerScoreSets = playerScore.getSets();
        int playerScoreGames = playerScore.getGames();
        PointScoreEnum playerScorePoints = playerScore.getPoints();

        int opponentScoreSets = opponentScore.getSets();
        int opponentScoreGames = opponentScore.getGames();
        PointScoreEnum opponentScorePoints = opponentScore.getPoints();


        if (isBothAdvantage(playerScorePoints, opponentScorePoints)) {
            playerScore.setPoints(FORTY);
            opponentScore.setPoints(FORTY);
            return;
        }

        if (isPointVictory(playerScorePoints, opponentScorePoints)) {
            playerScore.setPoints(PointScoreEnum.LOVE);
            opponentScore.setPoints(PointScoreEnum.LOVE);

            updateScoreGames(playerScore, playerScoreGames);

            playerScoreGames = playerScore.getGames();
            opponentScoreGames = opponentScore.getGames();
        }


        if (isGameVictory(playerScoreGames, opponentScoreGames)) {
            playerScore.setGames(0);
            opponentScore.setGames(0);

            updateScoreSet(playerScore, playerScoreSets);

            playerScoreSets = playerScore.getSets();
            opponentScoreSets = opponentScore.getSets();
        }

        if (isSetVictory(playerScoreSets, opponentScoreSets)) {
            System.out.println("*-*-*- *-* -*-*-*-*-*-* -* winnnnnnnn n ");
            // redirect to winPage
            return;
        }
    }

    private void addMatchPoint(Score playerScore) {      //PointScoreEnum firstPlayerPoint
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

    private void addTieBreakPoint(Score playerScore) {
        int updatedTieBreakPoints = playerScore.getTieBreakPoints() + 1;
        playerScore.setTieBreakPoints(updatedTieBreakPoints);
    }

    private boolean isTieBreakVictory(int playerTieBreakPoints, int opponentTieBreakPoints) {
        return (playerTieBreakPoints >= 7 && (playerTieBreakPoints - opponentTieBreakPoints >= 2));
    }

    private boolean isSetVictory(int playerScoreSets, int opponentScoreSets) {
        return ((playerScoreSets == 2 && (playerScoreSets - opponentScoreSets == 2)) ||
                (playerScoreSets == 2 && (playerScoreSets - opponentScoreSets == 1)));
    }

    private boolean isGameVictory(int playerScoreGames, int opponentScoreGames) {
        return ((playerScoreGames == 6 && (playerScoreGames - opponentScoreGames >= 2)) ||
                (playerScoreGames == 7 && (playerScoreGames - opponentScoreGames == 2)) ||
                (playerScoreGames == 7 && opponentScoreGames == 6));
    }

    private boolean isTieBreak(Score playerScore, Score opponentScore) {
        return (playerScore.getGames() == 6 && opponentScore.getGames() == 6);
    }

    private boolean isBothAdvantage(PointScoreEnum playerScore, PointScoreEnum opponentScore) {
        return (playerScore == ADVANTAGE && opponentScore == ADVANTAGE);
    }

    private boolean isPointVictory(PointScoreEnum playerScore, PointScoreEnum opponentScore) {
        return ((playerScore == PointScoreEnum.ADVANTAGE && opponentScore != FORTY) ||
                (playerScore == PointScoreEnum.WIN && opponentScore == FORTY));
    }

    private void updateScoreSet(Score playerScore, int playerScoreSets) {
        int updatedScoreSet = playerScoreSets + 1;
        playerScore.setSets(updatedScoreSet);
    }

    private void updateScoreGames(Score playerScore, int playerScoreGames) {
        int updatedScoreGame = playerScoreGames + 1;
        playerScore.setGames(updatedScoreGame);
    }

    private void updateForScoreSetAndScoreGame() {

    }

}
