package com.tennis.service.victory;

import com.tennis.model.MatchScoreModel;
import com.tennis.entity.Player;
import com.tennis.model.PlayerModel;

public class SetsVictoryAndWinner {
    private static final int AMOUNT_SETS_TO_WIN = 2;

    public boolean isSetVictory(int playerSetScore, int opponentSetScore) {
        return (playerSetScore == AMOUNT_SETS_TO_WIN && (playerSetScore > opponentSetScore));
    }

    public PlayerModel determineWinner(MatchScoreModel match) {
        PlayerModel player1 = match.getMatchModel().getPlayer1();
        int firstPlayerSetScore = match.getFirstPlayerScore().getSets();
        PlayerModel player2 = match.getMatchModel().getPlayer2();

        PlayerModel winner;
        if (firstPlayerSetScore == AMOUNT_SETS_TO_WIN) {
            winner = player1;
        } else {
            winner = player2;
        }
        return winner;
    }
}
