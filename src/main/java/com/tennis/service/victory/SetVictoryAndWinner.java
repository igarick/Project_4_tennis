package com.tennis.service.victory;

import com.tennis.model.MatchScoreModel;
import com.tennis.model.Player;

public class SetVictoryAndWinner {
    private static final int AMOUNT_SETS_TO_WIN = 2;

    public boolean isSetVictory(int playerSetScore, int opponentSetScore) {
        return (playerSetScore == AMOUNT_SETS_TO_WIN && (playerSetScore > opponentSetScore));
    }

    public Player determineWinner(MatchScoreModel match) {
        Player player1 = match.getMatch().getPlayer1();
        int firstPlayerSetScore = match.getFirstPlayerScore().getSets();
        Player player2 = match.getMatch().getPlayer2();

        Player winner;
        if (firstPlayerSetScore == AMOUNT_SETS_TO_WIN) {
            winner = player1;
        } else {
            winner = player2;
        }
        return winner;
    }
}
