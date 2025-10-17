package com.tennis.service;

import com.tennis.model.MatchScoreModel;
import com.tennis.model.Player;
import com.tennis.model.PointScoreEnum;
import com.tennis.model.Score;

public class MatchScoreCalculationService {
    private static final PointScoreEnum FORTY = PointScoreEnum.FORTY;
    private static final PointScoreEnum ADVANTAGE = PointScoreEnum.ADVANTAGE;
    private static final PointScoreEnum WIN = PointScoreEnum.WIN;
    private static final PointScoreEnum LOVE = PointScoreEnum.LOVE;

    public void updateScore(String playerIdParam, MatchScoreModel currentMatch) {
        Integer playerId = Integer.parseInt(playerIdParam);

        Integer firstPlayerId = currentMatch.getMatch().getPlayer1().getId();
        Integer secondPlayerId = currentMatch.getMatch().getPlayer2().getId();

        if (playerId.equals(firstPlayerId)) {
            Score firstPlayerScore = currentMatch.getFirstPlayerScore();
            Score secondPlayerScore = currentMatch.getSecondPlayerScore();

            addPoint(firstPlayerScore, secondPlayerScore, currentMatch);

            executeGameConditions(firstPlayerScore, secondPlayerScore, currentMatch);
        }

        if (playerId.equals(secondPlayerId)) {
            Score firstPlayerScore = currentMatch.getSecondPlayerScore();
            Score secondPlayerScore = currentMatch.getFirstPlayerScore();

            addPoint(firstPlayerScore, secondPlayerScore, currentMatch);

            executeGameConditions(firstPlayerScore, secondPlayerScore, currentMatch);
        }

    }

    private void addPoint(Score playerScore, Score opponentScore, MatchScoreModel currentMatch) {
        boolean tieBreak = isTieBreak(playerScore, opponentScore);
        currentMatch.getMatch().setTieBreak(tieBreak);

        if (tieBreak) {
            addTieBreakPoint(playerScore);
        } else {
            addMatchPoint(playerScore);
        }
    }

    private void executeGameConditions(Score playerScore, Score opponentScore, MatchScoreModel currentMatch) {
        if (currentMatch.getMatch().isTieBreak()) {
            handleTieBreak(playerScore, opponentScore);
        } else {
            handleNormalGame(playerScore, opponentScore);
        }

        handleGameVictory(playerScore, opponentScore);
        handleSetVictory(playerScore, opponentScore, currentMatch);
    }

    private void handleSetVictory(Score player, Score opponent, MatchScoreModel match) {
        if (isSetVictory(player.getSets(), opponent.getSets())) {
            Player player1 = match.getMatch().getPlayer1();
            int firstPlayerSetScore = match.getFirstPlayerScore().getSets();

            Player player2 = match.getMatch().getPlayer2();

            int setsWin = 2;

            Player winner;
            if (firstPlayerSetScore == setsWin) {
                winner = player1;
            } else {
                winner = player2;
            }
            match.getMatch().setWinner(winner);
            match.getMatch().setFinished(true);
        }
    }

    private void handleGameVictory(Score player, Score opponent) {
        if (isGameVictory(player.getGames(), opponent.getGames())) {
            player.setGames(0);
            opponent.setGames(0);

            incrementSetsScore(player);
        }
    }

    private void handleNormalGame(Score player, Score opponent) {
        if (isBothAdvantage(player.getPoints(), opponent.getPoints())) {
            player.setPoints(FORTY);
            opponent.setPoints(FORTY);
            return;
        }

        if (isPointVictory(player.getPoints(), opponent.getPoints())) {
            player.setPoints(LOVE);
            opponent.setPoints(LOVE);

            incrementGameScore(player);
        }
    }

    private void handleTieBreak(Score player, Score opponent) {
        if (isTieBreakVictory(player.getTieBreakPoints(), opponent.getTieBreakPoints())) {
            player.setTieBreakPoints(0);
            opponent.setTieBreakPoints(0);

            incrementGameScore(player);
        }
    }

    private void addMatchPoint(Score player) {
        PointScoreEnum point = player.getPoints();
        switch (point) {
            case LOVE:
                player.setPoints(PointScoreEnum.FIFTEEN);
                break;
            case FIFTEEN:
                player.setPoints(PointScoreEnum.THIRTY);
                break;
            case THIRTY:
                player.setPoints(PointScoreEnum.FORTY);
                break;
            case FORTY:
                player.setPoints(PointScoreEnum.ADVANTAGE);
                break;
            case ADVANTAGE:
                player.setPoints(PointScoreEnum.WIN);
                break;
            default:
                player.setPoints(point);
        }
    }

    private void addTieBreakPoint(Score player) {
        int updatedTieBreakPoints = player.getTieBreakPoints() + 1;
        player.setTieBreakPoints(updatedTieBreakPoints);
    }

    private boolean isTieBreakVictory(int playerTieBreakPoints, int opponentTieBreakPoints) {
        return (playerTieBreakPoints >= 7 && (playerTieBreakPoints - opponentTieBreakPoints >= 2));
    }

    private boolean isSetVictory(int playerSetScore, int opponentSetScore) {
        return (playerSetScore == 2 && (playerSetScore > opponentSetScore));
    }

    private boolean isGameVictory(int playerGameScore, int opponentGameScore) {
        return ((playerGameScore == 6 && (playerGameScore - opponentGameScore >= 2)) ||
                (playerGameScore == 7 && (playerGameScore - opponentGameScore == 2)) ||
                (playerGameScore == 7 && opponentGameScore == 6));
    }

    private boolean isTieBreak(Score player, Score opponent) {
        return (player.getGames() == 6 && opponent.getGames() == 6);
    }

    private boolean isBothAdvantage(PointScoreEnum playerPointScore, PointScoreEnum opponentPointScore) {
        return (playerPointScore == ADVANTAGE && opponentPointScore == ADVANTAGE);
    }

    private boolean isPointVictory(PointScoreEnum playerPointScore, PointScoreEnum opponentPointScore) {
        return ((playerPointScore == ADVANTAGE && opponentPointScore != FORTY) ||
                (playerPointScore == WIN && opponentPointScore == FORTY));
    }

    private void incrementSetsScore(Score player) {
        int updatedSet = player.getSets() + 1;
        player.setSets(updatedSet);
    }

    private void incrementGameScore(Score player) {
        int updatedGame = player.getGames() + 1;
        player.setGames(updatedGame);
    }

}
