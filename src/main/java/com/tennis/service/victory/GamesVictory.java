package com.tennis.service.victory;

import com.tennis.model.Score;

public class GamesVictory {
    private static final int ADVANTAGE_POINTS_FOR_WIN = 2;
    private static final int AMOUNT_GAMES_FOR_WIN_TILL_TIE_BREAK = 6;
    private static final int AMOUNT_GAMES_FOR_WIN_AFTER_TIE_BREAK = 7;

    public void handleGamesVictory(Score player, Score opponent) {
        if (isGamesVictory(player.getGames(), opponent.getGames())) {
            player.setGames(0);
            opponent.setGames(0);

            incrementSetsScore(player);
        }
    }

    private boolean isGamesVictory(int playerGameScore, int opponentGameScore) {
        return ((playerGameScore == AMOUNT_GAMES_FOR_WIN_TILL_TIE_BREAK &&
                 (playerGameScore - opponentGameScore >= ADVANTAGE_POINTS_FOR_WIN)) ||
                (playerGameScore == AMOUNT_GAMES_FOR_WIN_AFTER_TIE_BREAK &&
                 (playerGameScore - opponentGameScore == ADVANTAGE_POINTS_FOR_WIN)) ||
                (playerGameScore == AMOUNT_GAMES_FOR_WIN_AFTER_TIE_BREAK &&
                 opponentGameScore == AMOUNT_GAMES_FOR_WIN_TILL_TIE_BREAK));
    }

    private void incrementSetsScore(Score player) {
        int updatedSet = player.getSets() + 1;
        player.setSets(updatedSet);
    }
}
