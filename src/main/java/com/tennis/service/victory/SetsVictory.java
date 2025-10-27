package com.tennis.service.victory;

import com.tennis.model.MatchScoreModel;
import com.tennis.model.PlayerModel;
import com.tennis.model.Score;

public class SetsVictory {
    private static final int AMOUNT_SETS_TO_WIN = 2;

    public void handleSetsVictory(Score playerScore, Score opponentScore, MatchScoreModel currentMatch) {
        if (isSesVictory(playerScore.getSets(), opponentScore.getSets())) {
            currentMatch.getMatchModel().setFinished(true);

            PlayerModel player = determineWinner(currentMatch);

            currentMatch.getMatchModel().setWinner(player);
        }
    }

    public boolean isSesVictory(int playerSetScore, int opponentSetScore) {
        return (playerSetScore == AMOUNT_SETS_TO_WIN && (playerSetScore > opponentSetScore));
    }

    private PlayerModel determineWinner(MatchScoreModel match) {
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
