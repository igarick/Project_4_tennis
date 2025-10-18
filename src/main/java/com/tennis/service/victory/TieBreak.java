package com.tennis.service.victory;

import com.tennis.model.Score;

public class TieBreak {
    private static final int TIE_BREAK_POINTS_TO_WIN = 7;
    private static final int POINTS_ADVANTAGE_FOR_WIN = 2;


    public void handleTieBreak(Score player, Score opponent) {
        if (isTieBreakVictory(player.getTieBreakPoints(), opponent.getTieBreakPoints())) {
            player.setTieBreakPoints(0);
            opponent.setTieBreakPoints(0);

            incrementGameScore(player);
        }
    }

    private boolean isTieBreakVictory(int playerTieBreakPoints, int opponentTieBreakPoints) {
        return (playerTieBreakPoints >= TIE_BREAK_POINTS_TO_WIN &&
                (playerTieBreakPoints - opponentTieBreakPoints >= POINTS_ADVANTAGE_FOR_WIN));
    }

    private void incrementGameScore(Score player) {
        int updatedGame = player.getGames() + 1;
        player.setGames(updatedGame);
    }
}
