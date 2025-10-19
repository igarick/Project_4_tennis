package com.tennis.service.victory;

import com.tennis.model.PointScoreEnum;
import com.tennis.model.Score;

public class PointsVictoryAndAdvantage {
    private static final PointScoreEnum FORTY = PointScoreEnum.FORTY;
    private static final PointScoreEnum ADVANTAGE = PointScoreEnum.ADVANTAGE;
    private static final PointScoreEnum WIN = PointScoreEnum.WIN;
    private static final PointScoreEnum LOVE = PointScoreEnum.LOVE;

    public void handleNormalGame(Score player, Score opponent) {
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

    private boolean isBothAdvantage(PointScoreEnum playerPointScore, PointScoreEnum opponentPointScore) {
        return (playerPointScore == ADVANTAGE && opponentPointScore == ADVANTAGE);
    }

    private boolean isPointVictory(PointScoreEnum playerPointScore, PointScoreEnum opponentPointScore) {
        return ((playerPointScore == ADVANTAGE && opponentPointScore != FORTY) ||
                (playerPointScore == WIN && opponentPointScore == FORTY));
    }

    private void incrementGameScore(Score player) {
        int updatedGame = player.getGames() + 1;
        player.setGames(updatedGame);
    }
}
